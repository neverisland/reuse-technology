package cn.yang.authentication.authorization.user.service;

import cn.yang.authentication.authorization.user.dto.user.*;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.authentication.authorization.user.facade.UserFacade;
import cn.yang.authentication.authorization.user.repository.UserRepository;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.page.PageResult;
import cn.yang.common.data.structure.enums.EnabledEnum;
import cn.yang.authentication.authorization.enums.ErrorStatusCodeEnum;
import cn.yang.foundational.capability.username.UsernameGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 用户 业务层
 *
 * @author 未见清海
 */
@Service
public class UserService implements UserFacade {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UsernameGenerator usernameGenerator;

    /**
     * 分页查询用户数据
     *
     * @param userPageQuery 分页查询入参
     * @return 用户列表数据
     */
    @Override
    public PageResult<UserPageDto> pageData(UserPageQuery userPageQuery) {

        return userRepository.pageData(userPageQuery);
    }

    /**
     * 校验用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在 存在返回true，不存在返回false
     */
    @Override
    public Boolean judgeIsExistUsername(String username) {
        if (ObjectUtils.isEmpty(username)) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "用户名不能为空");
        }
        try {
            User user = userRepository.selectByUsername(username);
            return user != null;
        } catch (NullDataException e) {
            return false;
        }
    }

    /**
     * 校验手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在 存在返回true，不存在返回false
     */
    @Override
    public Boolean judgeIsExistPhone(String phone) {
        if (ObjectUtils.isEmpty(phone)) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "手机号不能为空");
        }
        try {
            User user = userRepository.selectByPhone(phone);
            return user != null;
        } catch (NullDataException e) {
            return false;
        }
    }

    /**
     * 新增用户数据
     *
     * @param userInsertDto 用户数据新增入参
     * @return 用户id
     */
    @Override
    public String insertUser(UserInsertDto userInsertDto) {
        // 判断手机号是否存在
        if (judgeIsExistPhone(userInsertDto.getPhone())) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_INSERT, "手机号已存在");
        }

        // 判断是否存在用户名和用户昵称,如果没有的话就自动生成
        if (ObjectUtils.isEmpty(userInsertDto.getUsername())) {
            userInsertDto.setUsername(getUsername());
        } else if (judgeIsExistUsername(userInsertDto.getUsername())) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_INSERT, "用户名已存在");
        }

        if (!StringUtils.hasLength(userInsertDto.getNickname())) {
            userInsertDto.setNickname(usernameGenerator.getNickname(6));
        }

        // 新增用户
        User user = BeanConvertUtils.convert(userInsertDto, User.class);
        user.setEnabled(EnabledEnum.ENABLE.getEnabled());
        user.setAccountNonLocked(AccountNonLockedEnum.ENABLE.getEnabled());
        return userRepository.insertData(user);
    }

    /**
     * 修改用户基础数据
     *
     * @param userUpdateDto 用户修改数据
     */
    @Override
    public void updateUser(UserUpdateDto userUpdateDto) {
        User dbUser;
        try {
            dbUser = userRepository.selectById(userUpdateDto.getId());
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }

        // 校验用户名是否重复
        if (!dbUser.getUsername().equals(userUpdateDto.getUsername()) && judgeIsExistUsername(userUpdateDto.getUsername())) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_UPDATED, "用户名已存在");
        }

        // 校验手机号是否重复
        if (!dbUser.getPhone().equals(userUpdateDto.getPhone()) && judgeIsExistPhone(userUpdateDto.getPhone())) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_CANNOT_BE_UPDATED, "手机号已存在");
        }

        // 修改用户数据
        User updateUser = BeanConvertUtils.convert(userUpdateDto, User.class);
        userRepository.updateData(updateUser);
    }

    /**
     * 根据用户id获取用户详情数据
     *
     * @param id 用户id
     * @return 用户详情数据
     */
    @Override
    public User selectById(String id) {
        try {
            return userRepository.selectById(id);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 禁用/启用 用户
     *
     * @param disableEnableUserDto 禁用/启用用户数据
     */
    @Override
    public void disableEnableUser(DisableEnableUserDto disableEnableUserDto) {
        // 持久化用户禁用状态
        try {
            User user = userRepository.selectById(disableEnableUserDto.getUserId());
            user.setEnabled(disableEnableUserDto.getEnabled());
            userRepository.disableEnableUser(user);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 根据账号名 / 手机号 获取用户数据
     *
     * @param username 账号 / 手机号
     * @return 用户数据
     * @throws NullDataException 用户数据不存在
     */
    @Override
    public User selectByUsernameOrPhone(String username) throws NullDataException {
        return userRepository.selectByUsernameOrPhone(username);
    }

    /**
     * 根据用户id删除用户数据
     *
     * @param id 用户id
     */
    @Override
    public void deleteById(String id) {
        int count = userRepository.deleteById(id);
        if (count == 0) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_DOES_NOT_EXIST, "用户不存在");
        }
    }

    /**
     * 获取随机用户名
     *
     * @return 用户名称
     */
    private String getUsername() {
        String username = usernameGenerator.getUsername(12);
        Boolean judgeIsExistUsername = judgeIsExistUsername(username);
        if (judgeIsExistUsername) {
            return getUsername();
        }
        return username;
    }
}




