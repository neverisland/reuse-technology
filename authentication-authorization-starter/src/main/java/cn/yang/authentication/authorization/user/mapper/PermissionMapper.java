package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.PermissionDo;
import cn.yang.authentication.authorization.user.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface PermissionMapper {

    /**
     * 新增数据
     *
     * @param permissionDo 新增数据
     * @return 新增条数
     */
    int insert(PermissionDo permissionDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param permissionDo 新增数据
     * @return 新增条数
     */
    int insertSelective(PermissionDo permissionDo);

    /**
     * 批量新增数据
     *
     * @param authorityList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<PermissionDo> authorityList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    Permission selectById(Long id);

    /**
     * 根据主键修改数据
     *
     * @param permissionDo 修改数据
     * @return 修改条数
     */
    int updateById(PermissionDo permissionDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param permissionDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(PermissionDo permissionDo);

    /**
     * 根据id删除数据
     *
     * @param id 数据id
     * @return 删除数据
     */
    int deleteById(String id);

    /**
     * 获取所有的权限列表
     *
     * @return 权限列表
     */
    List<PermissionDo> selectAllData();

    /**
     * 批量修改权限数据
     * @param permissionDoList 待修改的权限数据
     */
    void batchUpdateDate(@Param("list") List<PermissionDo> permissionDoList);

    /**
     * 根据权限id列表删除数据
     * @param deleteIdList 待删除的权限id列表
     */
    void deleteByIdList(@Param("list") List<String> deleteIdList);

    /**
     * 根据权限标识列表获取权限数据
     *
     * @param permissionMarkList 权限标识列表
     * @return 权限列表数据
     */
    List<PermissionDo> selectDataByMarks(@Param("list") List<String> permissionMarkList);

    /**
     * 根据权限id列表获取权限数据
     *
     * @param permissionIdList 权限id列表
     * @return 权限列表数据
     */
    List<PermissionDo> selectDataByIdList(@Param("list") List<String> permissionIdList);
}




