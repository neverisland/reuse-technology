package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.UserDo;
import cn.yang.authentication.authorization.user.dto.user.UserPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface UserMapper {

    /**
     * 新增数据
     *
     * @param userDo 新增数据
     * @return 新增条数
     */
    int insert(UserDo userDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param userDo 新增数据
     * @return 新增条数
     */
    int insertSelective(UserDo userDo);

    /**
     * 批量新增数据
     *
     * @param userDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<UserDo> userDoList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    UserDo selectById(String id);

    /**
     * 根据主键修改数据
     *
     * @param userDo 修改数据
     * @return 修改条数
     */
    int updateById(UserDo userDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param userDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(UserDo userDo);

    /**
     * 根据id删除数据
     *
     * @param id 数据id
     * @return 删除数据
     */
    int deleteById(String id);

    /**
     * 分页查询数据数量
     *
     * @param userPageQuery 分页查询数据入参
     * @return 分页数量
     */
    Integer selectPageCount(@Param("query") UserPageQuery userPageQuery);

    /**
     * 分页查询数据
     *
     * @param userPageQuery 分页查询数据入参
     * @return 分页数据
     */
    List<UserDo> selectPageData(@Param("query") UserPageQuery userPageQuery);

    /**
     * 根据用户名获取用户数据
     *
     * @param username 用户名
     * @return 用户数据
     */
    UserDo selectByUsername(@Param("username") String username);

    /**
     * 根据手机号获取用户数据
     *
     * @param phone 手机号
     * @return 用户数据
     */
    UserDo selectByPhone(@Param("phone") String phone);

    /**
     * 禁用 / 启用用户
     *
     * @param userDo 用户
     */
    void disableEnableUser(@Param("user") UserDo userDo);

    /**
     * 根据用户名 / 手机号 获取用户数据
     *
     * @param username 用户名 / 手机号
     * @return 用户数据
     */
    UserDo selectByUsernameOrPhone(@Param("username") String username);
}
