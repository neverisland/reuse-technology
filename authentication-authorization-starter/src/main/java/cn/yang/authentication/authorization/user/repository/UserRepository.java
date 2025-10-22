package cn.yang.authentication.authorization.user.repository;

import cn.yang.authentication.authorization.user.dal.UserDo;
import cn.yang.authentication.authorization.user.dto.user.UserPageDto;
import cn.yang.authentication.authorization.user.dto.user.UserPageQuery;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.authentication.authorization.user.mapper.UserMapper;
import cn.yang.common.data.structure.annotation.assignment.BaseDataAssignment;
import cn.yang.common.data.structure.annotation.assignment.DataOperationTypeEnum;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.page.PageResult;
import cn.yang.foundational.capability.id.generator.IdGenerator;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户 仓储层
 *
 * @author 未见清海
 */
@Component
public class UserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private IdGenerator idGenerator;

    /**
     * 分页查询数据
     *
     * @param userPageQuery 用户查询信息
     * @return 分页返回数据
     */
    public PageResult<UserPageDto> pageData(UserPageQuery userPageQuery) {
        Integer count = userMapper.selectPageCount(userPageQuery);

        if (count > 0) {
            List<UserDo> userDoList = userMapper.selectPageData(userPageQuery);
            List<UserPageDto> userPageDtoList = BeanConvertUtils.convert(userDoList, UserPageDto.class);
            return new PageResult<>(userPageQuery, userPageDtoList, count);
        } else {
            return new PageResult<>(userPageQuery);
        }
    }

    /**
     * 根据用户名获取用户数据
     *
     * @param username 用户名
     * @return 用户数据
     */
    @Cacheable(cacheNames = "user-username", key = "#username")
    public User selectByUsername(String username) throws NullDataException {
        UserDo userDo = userMapper.selectByUsername(username);
        if (userDo == null) {
            throw new NullDataException("用户数据不存在");
        }
        return BeanConvertUtils.convert(userDo, User.class);
    }

    /**
     * 根据手机号获取用户数据
     *
     * @param phone 手机号
     * @return 用户数据
     */
    @Cacheable(cacheNames = "user-phone", key = "#phone")
    public User selectByPhone(String phone) throws NullDataException {
        UserDo userDo = userMapper.selectByPhone(phone);
        if (userDo == null) {
            throw new NullDataException("用户数据不存在");
        }
        return BeanConvertUtils.convert(userDo, User.class);
    }

    /**
     * 新增用户数据
     *
     * @param user 用户实体
     * @return 用户id
     */
    @BaseDataAssignment
    public String insertData(User user) {
        UserDo userDo = BeanConvertUtils.convert(user, UserDo.class);
        userDo.setId(idGenerator.getId());
        userMapper.insert(userDo);
        return userDo.getId();
    }

    /**
     * 根据用户id获取用户数据
     *
     * @param id 用户id
     * @return 用户详情数据
     */
    public User selectById(String id) throws NullDataException {
        UserDo userDo = userMapper.selectById(id);
        if (userDo == null) {
            throw new NullDataException("用户不存在");
        }
        return BeanConvertUtils.convert(userDo, User.class);
    }

    /**
     * 修改用户数据
     *
     * @param updateUser 待修改的用户数据
     */
    @BaseDataAssignment(DataOperationTypeEnum.UPDATE)
    public void updateData(User updateUser) {
        UserDo userDo = BeanConvertUtils.convert(updateUser, UserDo.class);
        userMapper.updateById(userDo);
    }

    /**
     * 禁用启用用户数据
     *
     * @param user 待修改禁用状态的用户
     */
    @BaseDataAssignment(DataOperationTypeEnum.UPDATE)
    public void disableEnableUser(User user) {
        UserDo userDo = BeanConvertUtils.convert(user, UserDo.class);
        userMapper.disableEnableUser(userDo);
    }

    /**
     * 根据用户名 / 手机号 获取用户数据
     *
     * @param username 用户名 / 手机号
     * @return 用户数据
     */
    public User selectByUsernameOrPhone(String username) throws NullDataException {
        UserDo userDo = userMapper.selectByUsernameOrPhone(username);
        if (userDo == null) {
            throw new NullDataException("用户不存在");
        }
        return BeanConvertUtils.convert(userDo, User.class);
    }

    /**
     * 根据用户id删除用户数据
     *
     * @param id 用户id
     */
    public int deleteById(String id) {
        return userMapper.deleteById(id);
    }
}




