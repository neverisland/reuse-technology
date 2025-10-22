package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.RolePermissionDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface RolePermissionMapper {

    /**
     * 批量新增数据
     *
     * @param rolePermissionDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<RolePermissionDo> rolePermissionDoList);

    /**
     * 根据角色id删除角色权限关联数据
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(@Param("roleId") String roleId);
}




