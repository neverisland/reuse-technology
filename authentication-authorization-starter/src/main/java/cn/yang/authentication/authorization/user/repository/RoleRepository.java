package cn.yang.authentication.authorization.user.repository;

import cn.yang.authentication.authorization.user.dal.RoleDo;
import cn.yang.authentication.authorization.user.dal.RolePermissionDo;
import cn.yang.authentication.authorization.user.dto.role.RolePageDto;
import cn.yang.authentication.authorization.user.dto.role.RolePageQuery;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.authentication.authorization.user.entity.RolePermission;
import cn.yang.authentication.authorization.user.mapper.RoleMapper;
import cn.yang.authentication.authorization.user.mapper.RolePermissionMapper;
import cn.yang.common.data.structure.annotation.assignment.BaseDataAssignment;
import cn.yang.common.data.structure.annotation.assignment.DataOperationTypeEnum;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.page.PageResult;
import cn.yang.foundational.capability.id.generator.IdGenerator;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色表 仓储层
 *
 * @author 未见清海
 */
@Component
public class RoleRepository {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 获取所有的内置角色数据
     *
     * @return 所有的角色数据
     */
    @Cacheable(cacheNames = "role-all")
    public List<Role> selectAllBuiltInData() {
        List<RoleDo> roleDoList = roleMapper.selectAllBuiltInData();
        return BeanConvertUtils.convert(roleDoList, Role.class);
    }

    /**
     * 批量新增内置角色数据
     *
     * @param roleList 内置的角色列表
     */
    @BaseDataAssignment
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void batchInsertData(List<Role> roleList) {
        List<RoleDo> roleDoList = new ArrayList<>();
        List<RolePermission> rolePermissionList = new ArrayList<>();

        roleList.forEach(role -> {
            RoleDo roleDo = BeanConvertUtils.convert(role, RoleDo.class);
            roleDo.setId(idGenerator.getId());
            roleDo.setCreateUserId("");
            roleDo.setUpdateUserId("");
            roleDoList.add(roleDo);
            // 新增角色权限关联数据
            if (!CollectionUtils.isEmpty(role.getPermissionList())) {
                role.getPermissionList().forEach(permission -> {
                    RolePermission rolePermission = new RolePermission(roleDo.getId(), permission.getId());
                    rolePermission.setId(idGenerator.getId());
                    rolePermissionList.add(rolePermission);
                });
            }
        });
        // 新增角色和角色权限关联数据
        roleMapper.insertBatch(roleDoList);

        if (!CollectionUtils.isEmpty(rolePermissionList)) {
            List<RolePermissionDo> rolePermissionDoList = BeanConvertUtils.convert(rolePermissionList, RolePermissionDo.class);
            rolePermissionMapper.insertBatch(rolePermissionDoList);
        }

    }

    /**
     * 批量修改角色数据
     *
     * @param updateDataList 修改的角色数据
     */
    @BaseDataAssignment(DataOperationTypeEnum.UPDATE)
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void batchUpdateDate(List<Role> updateDataList) {
        List<RoleDo> roleDoList = BeanConvertUtils.convert(updateDataList, RoleDo.class);
        roleMapper.batchUpdateDate(roleDoList);
    }

    /**
     * 根据角色id列表删除角色数据
     *
     * @param deleteIdList 角色id列表
     */
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void deleteByIdList(List<String> deleteIdList) {
        roleMapper.deleteByIdList(deleteIdList);
    }

    /**
     * 新增自定义角色
     *
     * @param insertRole 新增自定义角色
     */
    @Transactional
    @BaseDataAssignment
    @CacheEvict(cacheNames = {"role-all"})
    public String insertData(Role insertRole) {
        RoleDo roleDo = BeanConvertUtils.convert(insertRole, RoleDo.class);
        roleDo.setId(idGenerator.getId());
        roleDo.setMark(idGenerator.get32UUID());
        // 新增角色权限关联数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(insertRole.getPermissionList())) {
            insertRole.getPermissionList().forEach(permission -> {
                RolePermission rolePermission = new RolePermission(roleDo.getId(), permission.getId());
                rolePermission.setId(idGenerator.getId());
                rolePermissionList.add(rolePermission);
            });
        }

        // 新增数据
        roleMapper.insert(roleDo);

        if (!CollectionUtils.isEmpty(rolePermissionList)) {
            List<RolePermissionDo> rolePermissionDoList = BeanConvertUtils.convert(rolePermissionList, RolePermissionDo.class);
            rolePermissionMapper.insertBatch(rolePermissionDoList);
        }

        return roleDo.getId();
    }

    /**
     * 根据角色id获取角色详情
     *
     * @param id 角色id
     * @return 角色详情
     */
    @Cacheable(cacheNames = "role-id", key = "#id")
    public Role selectById(String id) throws NullDataException {
        RoleDo roleDo = roleMapper.selectById(id);
        if (roleDo == null) {
            throw new NullDataException("角色数据不存在");
        }
        Role role = BeanConvertUtils.convert(roleDo, Role.class);
        role.setPermissionList(BeanConvertUtils.convert(roleDo.getPermissionList(), Permission.class));
        return role;
    }

    /**
     * 修改角色数据
     *
     * @param updateRole 待修改的角色数据
     */
    @BaseDataAssignment(DataOperationTypeEnum.UPDATE)
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void updateData(Role updateRole) {
        RoleDo roleDo = BeanConvertUtils.convert(updateRole, RoleDo.class);
        roleMapper.updateById(roleDo);
    }

    /**
     * 修改角色和权限的关联数据
     *
     * @param roleId           角色id
     * @param permissionIdList 角色关联的权限
     */
    @Transactional
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void updateRolePermissionData(String roleId, List<String> permissionIdList) {
        rolePermissionMapper.deleteByRoleId(roleId);

        // 新增角色权限关联数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        permissionIdList.forEach(permissionId -> {
            RolePermission rolePermission = new RolePermission(roleId, permissionId);
            rolePermission.setId(idGenerator.getId());
            rolePermissionList.add(rolePermission);
        });
        if (!CollectionUtils.isEmpty(rolePermissionList)) {
            List<RolePermissionDo> rolePermissionDoList = BeanConvertUtils.convert(rolePermissionList, RolePermissionDo.class);
            rolePermissionMapper.insertBatch(rolePermissionDoList);
        }
    }

    /**
     * 分页获取角色数据
     *
     * @param rolePageQuery 角色数据分页入参
     * @return 分页返回值
     */
    public PageResult<RolePageDto> pageData(RolePageQuery rolePageQuery) {
        Integer count = roleMapper.selectPageCount(rolePageQuery);

        if (count > 0) {
            List<RoleDo> roleDoList = roleMapper.selectPageData(rolePageQuery);
            List<RolePageDto> rolePageDtoList = BeanConvertUtils.convert(roleDoList, RolePageDto.class);
            return new PageResult<>(rolePageQuery, rolePageDtoList, count);
        } else {
            return new PageResult<>(rolePageQuery);
        }
    }

    /**
     * 根据角色id删除角色
     *
     * @param id 角色id
     */
    @CacheEvict(cacheNames = {"role-all", "role-id", "role-name", "role-ids", "role-mark"}, allEntries = true)
    public void deleteById(String id) {
        roleMapper.deleteById(id);
    }

    /**
     * 根据角色名字获取角色数据
     *
     * @param name 角色名称
     * @return 角色数据
     */
    @Cacheable(cacheNames = "role-name", key = "#name")
    public Role selectByName(String name) throws NullDataException {
        RoleDo roleDo = roleMapper.selectByName(name);
        if (roleDo == null) {
            throw new NullDataException("未查询到角色数据");
        }
        return BeanConvertUtils.convert(roleDo, Role.class);
    }

    /**
     * 根据角色id列表获取角色列表数据
     *
     * @param roleIds 角色id列表
     * @return 角色列表数据
     */
    @Cacheable(cacheNames = "role-ids", key = "#roleIds")
    public List<Role> selectByIds(List<String> roleIds) {
        List<RoleDo> roleDoList = roleMapper.selectDetailListByIds(roleIds);
        List<Role> returnRoleList = new ArrayList<>();

        // 组装角色的权限数据
        roleDoList.forEach(roleDo -> {
            Role role = BeanConvertUtils.convert(roleDo, Role.class);
            role.setPermissionList(BeanConvertUtils.convert(roleDo.getPermissionList(), Permission.class));
            returnRoleList.add(role);
        });

        return returnRoleList;
    }

    /**
     * 根据角色标识获取角色详情
     *
     * @param mark 角色标识
     * @return 角色详情
     */
    @Cacheable(cacheNames = "role-mark", key = "#mark")
    public Role selectByMark(String mark) throws NullDataException {
        RoleDo roleDo = roleMapper.selectByMark(mark);
        if (roleDo == null) {
            throw new NullDataException("角色数据不存在");
        }
        Role role = BeanConvertUtils.convert(roleDo, Role.class);
        role.setPermissionList(BeanConvertUtils.convert(roleDo.getPermissionList(), Permission.class));
        return role;
    }
}




