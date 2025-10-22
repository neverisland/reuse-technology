package cn.yang.authentication.authorization.authentication.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.yang.authentication.authorization.authentication.entity.UserInfo;
import cn.yang.authentication.authorization.authentication.facade.AuthenticationFacade;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.UserFacade;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证业务
 *
 * @author : 未见清海
 */
@Service
public class AuthenticationService implements AuthenticationFacade {

    @Resource
    private UserFacade userFacade;

    @Resource
    private IdentityFacade identityFacade;


    /**
     * 根据用户id获取可进行的认证方式
     *
     * @param userId 用户id
     * @return 用户可进行的认证方式列表
     */
    @Override
    public List<Integer> selectAuthenticationMethod(String userId) {
        // todo

        return List.of();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息ø
     */
    @Override
    public UserInfo getCurrentUserInfo() {
        // 获取当前登录用户的身份id
        String identityId = getCurrentUserIdentityId();
        // 获取用户ID并且获取用户信息数据
        String userId = identityFacade.selectUserIdByIdentityId(identityId);
        User user = userFacade.selectById(userId);
        // 组装当前用户信息数据
        UserInfo userInfo = BeanConvertUtils.convert(user, UserInfo.class);
        userInfo.setUserId(userId);
        userInfo.setIdentityId(identityId);
        return userInfo;
    }

    /**
     * 获取当前登录用户的身份id
     *
     * @return 身份idø
     */
    @Override
    public String getCurrentUserIdentityId() {
        return String.valueOf(StpUtil.getLoginId());
    }

    /**
     * 校验当前用户是否已经认证
     *
     * @return 是否认证
     */
    @Override
    public Boolean verifyWhetherAuthenticated() {
        return StpUtil.isLogin();
    }
}
