package cn.yang.authentication.authorization.user.facade;

import cn.yang.authentication.authorization.user.dto.role.*;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.common.data.structure.vo.page.PageResult;

import java.util.List;

/**
 * 角色表 业务接口定义
 *
 * @author 未见清海
 */
public interface RoleFacade {

    /**
     * 角色初始化
     */
    void init();

    /**
     * 新增角色
     *
     * @param roleInsertDto 新增角色入参
     * @return 角色id
     */
    String insertData(RoleInsertDto roleInsertDto);

    /**
     * 修改自定义角色
     *
     * @param roleUpdateDto 修改自定义角色入参
     */
    void updateData(RoleUpdateDto roleUpdateDto);

    /**
     * 获取角色详情数据
     *
     * @param id 角色id
     * @return 角色详情
     */
    RoleDto selectById(String id);

    /**
     * 分页获取角色数据
     *
     * @param rolePageQuery 角色数据分页入参
     * @return 分页返回值
     */
    PageResult<RolePageDto> pageData(RolePageQuery rolePageQuery);

    /**
     * 根据角色id删除自定义角色
     *
     * @param id 角色id
     */
    void deleteById(String id);

    /**
     * 根据角色id列表获取角色列表数据
     *
     * @param roleIds 角色id列表
     * @return 角色列表数据
     */
    List<Role> selectByIds(List<String> roleIds);

    /**
     * 获取角色详情数据
     *
     * @param mark 角色标识
     * @return 角色详情
     */
    RoleDto selectByMark(String mark);

    /**
     * 根据角色ID列表获取权限列表数据
     *
     * @param roleIds 角色id列表
     * @return 权限列表数据
     */
    List<Permission> selectPermissionListByRoleIds(List<String> roleIds);
}
