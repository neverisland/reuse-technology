package cn.yang.authentication.authorization.user.facade;

import cn.yang.authentication.authorization.user.dto.user.*;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.vo.page.PageResult;

/**
 * 用户 业务接口定义
 *
 * @author 未见清海
 */
public interface UserFacade {

    /**
     * 分页查询用户数据
     *
     * @param userPageQuery 分页查询入参
     * @return 用户列表数据
     */
    PageResult<UserPageDto> pageData(UserPageQuery userPageQuery);

    /**
     * 校验用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在 存在返回true，不存在返回false
     */
    Boolean judgeIsExistUsername(String username);

    /**
     * 校验手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在 存在返回true，不存在返回false
     */
    Boolean judgeIsExistPhone(String phone);

    /**
     * 新增用户数据
     *
     * @param userInsertDto 用户数据新增入参
     * @return 用户id
     */
    String insertUser(UserInsertDto userInsertDto);

    /**
     * 修改用户基础数据
     *
     * @param userUpdateDto 用户修改数据
     */
    void updateUser(UserUpdateDto userUpdateDto);

    /**
     * 根据用户id获取用户详情数据
     *
     * @param id 用户id
     * @return 用户详情数据
     */
    User selectById(String id);

    /**
     * 禁用/启用 用户
     *
     * @param disableEnableUserDto 禁用/启用用户数据
     */
    void disableEnableUser(DisableEnableUserDto disableEnableUserDto);

    /**
     * 根据账号名 / 手机号 获取用户数据
     *
     * @param username 账号 / 手机号
     * @return 用户数据
     * @throws NullDataException 用户数据不存在
     */
    User selectByUsernameOrPhone(String username) throws NullDataException;

    /**
     * 根据用户id删除用户数据
     *
     * @param id 用户id
     */
    void deleteById(String id);

}
