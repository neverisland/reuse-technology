package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.RoleDo;
import cn.yang.authentication.authorization.user.dto.role.RolePageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface RoleMapper {

    /**
     * 新增数据
     *
     * @param roleDo 新增数据
     * @return 新增条数
     */
    int insert(RoleDo roleDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param roleDo 新增数据
     * @return 新增条数
     */
    int insertSelective(RoleDo roleDo);

    /**
     * 批量新增数据
     *
     * @param roleList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<RoleDo> roleList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    RoleDo selectById(String id);

    /**
     * 根据主键修改数据
     *
     * @param roleDo 修改数据
     * @return 修改条数
     */
    int updateById(RoleDo roleDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param roleDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(RoleDo roleDo);

    /**
     * 根据id删除数据
     *
     * @param id 数据id
     * @return 删除数据
     */
    int deleteById(@Param("id") String id);

    /**
     * 获取所有的角色数据
     *
     * @return 所有角色数据列表
     */
    List<RoleDo> selectAllBuiltInData();

    /**
     * 批量修改角色数据
     *
     * @param roleDoList 待修改的角色数据
     */
    void batchUpdateDate(@Param("list") List<RoleDo> roleDoList);

    /**
     * 根据角色id列表删除角色数据
     *
     * @param deleteIdList 角色id列表
     */
    void deleteByIdList(@Param("list") List<String> deleteIdList);

    /**
     * 获取分页检索数量
     *
     * @param rolePageQuery 分页查询条件
     * @return 分页数量
     */
    Integer selectPageCount(@Param("query") RolePageQuery rolePageQuery);

    /**
     * 获取分页数据
     *
     * @param rolePageQuery 分页检索数据入参
     * @return 分页数据
     */
    List<RoleDo> selectPageData(@Param("query") RolePageQuery rolePageQuery);

    /**
     * 根据角色名称获取角色数据
     *
     * @param name 角色名称
     * @return 角色数据
     */
    RoleDo selectByName(@Param("name") String name);

    /**
     * 根据角色id列表获取角色列表数据
     *
     * @param roleIds 角色id列表
     * @return 角色列表数据
     */
    List<RoleDo> selectByIds(@Param("list") List<String> roleIds);

    /**
     * 根据角色id列表获取角色详情列表数据
     *
     * @param roleIds 角色id列表
     * @return 角色列表数据
     */
    List<RoleDo> selectDetailListByIds(@Param("list") List<String> roleIds);

    /**
     * 根据角色标识获取角色数据
     *
     * @param mark 角色标识
     * @return 角色数据
     */
    RoleDo selectByMark(@Param("mark") String mark);
}




