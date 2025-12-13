package cn.yang.authentication.authorization.user.service;

import cn.yang.authentication.authorization.user.dto.permission.PermissionDto;
import cn.yang.authentication.authorization.user.dto.role.*;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.authentication.authorization.user.enums.PermissionEnum;
import cn.yang.authentication.authorization.user.enums.RoleEnum;
import cn.yang.authentication.authorization.user.enums.RoleTypeEnum;
import cn.yang.authentication.authorization.user.facade.PermissionFacade;
import cn.yang.authentication.authorization.user.facade.RoleFacade;
import cn.yang.authentication.authorization.user.repository.RoleRepository;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.page.PageResult;
import cn.yang.authentication.authorization.enums.ErrorStatusCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色表 业务层
 *
 * @author 未见清海
 */
@Service
public class RoleService implements RoleFacade {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private PermissionFacade permissionFacade;

    /**
     * 角色初始化
     */
    @Override
    public void init() {
        RoleEnum[] roleEnums = RoleEnum.values();
        List<Role> dbRoleList = roleRepository.selectAllBuiltInData();

        // 校验差异
        Map<String, Role> roleMap = dbRoleList.stream().collect(Collectors.toMap(Role::getMark, Function.identity()));

        List<RoleEnum> addDataList = new ArrayList<>();
        List<Role> updateDataList = new ArrayList<>();
        List<String> deleteIdList = new ArrayList<>();

        for (RoleEnum roleEnum : roleEnums) {
            Role dbRole = roleMap.get(roleEnum.getMark());
            if (dbRole == null) {
                addDataList.add(roleEnum);
                continue;
            }

            if (!roleEnum.getDescription().equals(dbRole.getDescription()) || !roleEnum.getName().equals(dbRole.getName())) {
                dbRole.setName(roleEnum.getName());
                dbRole.setDescription(roleEnum.getDescription());
                updateDataList.add(dbRole);
            }
        }

        dbRoleList.forEach(role -> {
            try {
                RoleEnum.getRoleEnumByMark(role.getMark());
            } catch (NullDataException e) {
                deleteIdList.add(role.getId());
            }
        });

        // 处理角色数据
        if (!CollectionUtils.isEmpty(addDataList)) {
            addRoleHandle(addDataList);
        }
        if (!CollectionUtils.isEmpty(updateDataList)) {
            roleRepository.batchUpdateDate(updateDataList);
        }
        if (!CollectionUtils.isEmpty(deleteIdList)) {
            roleRepository.deleteByIdList(deleteIdList);
        }
    }

    /**
     * 批量新增角色的处理
     *
     * @param roleEnumList 新增的角色枚举
     */
    private void addRoleHandle(List<RoleEnum> roleEnumList) {
        // 获取对应的权限列表
        HashSet<PermissionEnum> permissionEnums = new HashSet<>();
        for (RoleEnum roleEnum : roleEnumList) {
            permissionEnums.addAll(roleEnum.getPermissionList());
        }
        List<String> permissionMarkList = permissionEnums.stream().map(PermissionEnum::getMark).toList();
        List<Permission> permissionList = permissionFacade.selectDataByMarks(permissionMarkList);
        Map<String, Permission> permissionMap = permissionList.stream().collect(Collectors.toMap(Permission::getMark, Function.identity()));
        // 新增的角色数据处理
        List<Role> addRoleList = new ArrayList<>();
        for (RoleEnum roleEnum : roleEnumList) {
            Role role = BeanConvertUtils.convert(roleEnum, Role.class);
            role.setType(RoleTypeEnum.BUILT_IN_ROLE.getType());
            // 角色关联的权限数据
            List<PermissionEnum> permissionEnumList = roleEnum.getPermissionList();
            List<Permission> permissions = new ArrayList<>();
            for (PermissionEnum permissionEnum : permissionEnumList) {
                permissions.add(permissionMap.get(permissionEnum.getMark()));
            }
            role.setPermissionList(permissions);

            addRoleList.add(role);
        }
        roleRepository.batchInsertData(addRoleList);
    }

    /**
     * 新增角色
     *
     * @param roleInsertDto 新增角色入参
     * @return 角色id
     */
    @Override
    public String insertData(RoleInsertDto roleInsertDto) {
        Role insertRole = BeanConvertUtils.convert(roleInsertDto, Role.class);
        insertRole.setType(RoleTypeEnum.CUSTOMIZE_ROLE.getType());

        try {
            roleRepository.selectByName(roleInsertDto.getName());
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "已存在相同名称的角色数据");
        } catch (NullDataException e) {
            // not handle
        }

        // 校验权限数据是否正确
        List<Permission> permissionList = permissionFacade.selectDataByIdList(roleInsertDto.getPermissionIdList());
        if (CollectionUtils.isEmpty(permissionList)) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "新增角色数据参数异常");
        }
        insertRole.setPermissionList(permissionList);
        return roleRepository.insertData(insertRole);
    }

    /**
     * 修改自定义角色
     *
     * @param roleUpdateDto 修改自定义角色入参
     */
    @Override
    @Transactional
    public void updateData(RoleUpdateDto roleUpdateDto) {
        Role updateRole = BeanConvertUtils.convert(roleUpdateDto, Role.class);

        // 获取原来数据
        Role role;
        try {
            role = roleRepository.selectById(updateRole.getId());
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }
        if (RoleTypeEnum.BUILT_IN_ROLE.getType().equals(role.getType())) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_UPDATED, "该角色不允许修改");
        }

        // 校验重复名称
        try {
            Role sameNameRole = roleRepository.selectByName(roleUpdateDto.getName());
            if (sameNameRole != null && !sameNameRole.getId().equals(role.getId())) {
                throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "已存在相同角色名称");
            }
        } catch (NullDataException e) {
            // not handle
        }

        // 修改角色数据
        if (!role.getName().equals(roleUpdateDto.getName()) || !role.getDescription().equals(roleUpdateDto.getDescription())) {
            roleRepository.updateData(updateRole);
        }

        // 权限数据未修改的话不进行调整
        List<String> permissionIds = role.getPermissionList().stream().map(Permission::getId).toList();
        if (!roleUpdateDto.getPermissionIdList().equals(permissionIds)) {
            roleRepository.updateRolePermissionData(role.getId(), roleUpdateDto.getPermissionIdList());
        }
    }

    /**
     * 获取角色详情数据
     *
     * @param id 角色id
     * @return 角色详情
     */
    @Override
    public RoleDto selectById(String id) {
        Role role;
        try {
            role = roleRepository.selectById(id);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }
        RoleDto roleDto = BeanConvertUtils.convert(role, RoleDto.class);
        roleDto.setPermissionList(BeanConvertUtils.convert(role.getPermissionList(), PermissionDto.class));
        return roleDto;
    }

    /**
     * 分页获取角色数据
     *
     * @param rolePageQuery 角色数据分页入参
     * @return 分页返回值
     */
    @Override
    public PageResult<RolePageDto> pageData(RolePageQuery rolePageQuery) {
        return roleRepository.pageData(rolePageQuery);
    }

    /**
     * 根据角色id删除自定义角色
     *
     * @param id 角色id
     */
    @Override
    public void deleteById(String id) {
        try {
            Role role = roleRepository.selectById(id);
            if (RoleTypeEnum.BUILT_IN_ROLE.getType().equals(role.getType())) {
                throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_UPDATED, "当前角色不能被删除");
            }
            roleRepository.deleteById(id);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "无可用角色被删除");
        }
    }

    /**
     * 根据角色id列表获取角色列表数据
     *
     * @param roleIds 角色id列表
     * @return 角色列表数据
     */
    @Override
    public List<Role> selectByIds(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "角色id列表不能为空");
        }
        return roleRepository.selectByIds(roleIds);
    }

    /**
     * 获取角色详情数据
     *
     * @param mark 角色标识
     * @return 角色详情
     */
    @Override
    public RoleDto selectByMark(String mark) {
        Role role;
        try {
            role = roleRepository.selectByMark(mark);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }
        RoleDto roleDto = BeanConvertUtils.convert(role, RoleDto.class);
        roleDto.setPermissionList(BeanConvertUtils.convert(role.getPermissionList(), PermissionDto.class));
        return roleDto;
    }

    /**
     * 根据角色ID列表获取权限列表数据
     *
     * @param roleIds 角色id列表
     * @return 权限列表数据
     */
    @Override
    public List<Permission> selectPermissionListByRoleIds(List<String> roleIds) {
        List<Role> roleList = selectByIds(roleIds);

        return roleList.stream()
                .filter(Objects::nonNull)
                .flatMap(role -> {
                    List<Permission> permissions = role.getPermissionList();
                    return permissions != null ? permissions.stream() : Stream.<Permission>empty();
                })
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }
}




