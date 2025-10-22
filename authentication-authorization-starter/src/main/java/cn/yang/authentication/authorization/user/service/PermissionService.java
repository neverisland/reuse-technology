package cn.yang.authentication.authorization.user.service;

import cn.yang.authentication.authorization.user.dto.permission.PermissionDto;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.enums.PermissionEnum;
import cn.yang.authentication.authorization.user.facade.PermissionFacade;
import cn.yang.authentication.authorization.user.repository.PermissionRepository;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 权限表 业务层
 *
 * @author 未见清海
 */
@Service
public class PermissionService implements PermissionFacade {

    @Resource
    private PermissionRepository permissionRepository;

    /**
     * 权限初始化
     */
    @Override
    public void init() {
        // 获取权限列表
        PermissionEnum[] permissionEnums = PermissionEnum.values();
        List<Permission> dbPermissionList = permissionRepository.selectAllData();

        // 校验差异
        Map<String, Permission> authorityMap = dbPermissionList.stream().collect(Collectors.toMap(Permission::getMark, Function.identity()));

        List<PermissionEnum> addDataList = new ArrayList<>();
        List<Permission> updateDataList = new ArrayList<>();
        List<String> deleteIdList = new ArrayList<>();

        for (PermissionEnum permissionEnum : permissionEnums) {
            Permission dbPermission = authorityMap.get(permissionEnum.getMark());
            if (dbPermission == null) {
                addDataList.add(permissionEnum);
                continue;
            }
            if (!permissionEnum.getDescription().equals(dbPermission.getDescription())) {
                dbPermission.setDescription(permissionEnum.getDescription());
                updateDataList.add(dbPermission);
            }
        }

        // 需要删除的
        dbPermissionList.forEach(permission -> {
            try {
                PermissionEnum.getPermissionEnumByMark(permission.getMark());
            } catch (NullDataException e) {
                deleteIdList.add(permission.getId());
            }
        });

        // 处理权限数据
        if (!CollectionUtils.isEmpty(addDataList)) {
            permissionRepository.batchInsertData(addDataList);
        }
        if (!CollectionUtils.isEmpty(updateDataList)) {
            permissionRepository.batchUpdateDate(updateDataList);
        }
        if (!CollectionUtils.isEmpty(deleteIdList)) {
            permissionRepository.deleteByIdList(deleteIdList);
        }
    }

    /**
     * 根据权限标识列表获取权限数据
     *
     * @param permissionMarkList 权限标识列表
     * @return 权限列表数据
     */
    @Override
    public List<Permission> selectDataByMarks(List<String> permissionMarkList) {
        if (CollectionUtils.isEmpty(permissionMarkList)) {
            return List.of();
        }
        return permissionRepository.selectDataByMarks(permissionMarkList);
    }

    /**
     * 根据权限id列表获取权限数据
     *
     * @param permissionIdList 权限id列表
     * @return 权限列表数据
     */
    @Override
    public List<Permission> selectDataByIdList(List<String> permissionIdList) {
        if (CollectionUtils.isEmpty(permissionIdList)) {
            return List.of();
        }
        return permissionRepository.selectDataByIdList(permissionIdList);
    }

    /**
     * 获取所有的权限数据
     *
     * @return 所有的权限
     */
    @Override
    public List<PermissionDto> selectAllData() {
        List<Permission> permissionList = permissionRepository.selectAllData();
        return BeanConvertUtils.convert(permissionList, PermissionDto.class);
    }
}




