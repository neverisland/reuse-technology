package cn.yang.authentication.authorization.user.facade;

import cn.yang.authentication.authorization.user.dto.permission.PermissionDto;
import cn.yang.authentication.authorization.user.entity.Permission;

import java.util.List;

/**
 * 权限 业务接口定义
 *
 * @author 未见清海
 */
public interface PermissionFacade {

    /**
     * 权限初始化
     */
    void init();

    /**
     * 根据权限标识列表获取权限数据
     *
     * @param permissionMarkList 权限标识列表
     * @return 权限列表数据
     */
    List<Permission> selectDataByMarks(List<String> permissionMarkList);

    /**
     * 根据权限id列表获取权限数据
     *
     * @param permissionIdList 权限id列表
     * @return 权限列表数据
     */
    List<Permission> selectDataByIdList(List<String> permissionIdList);

    /**
     * 获取所有的权限数据
     *
     * @return 所有的权限
     */
    List<PermissionDto> selectAllData();
}
