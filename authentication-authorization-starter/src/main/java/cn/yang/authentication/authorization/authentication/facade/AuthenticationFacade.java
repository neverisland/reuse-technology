package cn.yang.authentication.authorization.authentication.facade;

import cn.yang.authentication.authorization.authentication.entity.UserInfo;

import java.util.List;

/**
 * 认证接口
 *
 * @author : 未见清海
 */
public interface AuthenticationFacade {

    /**
     * 根据用户id获取可进行的认证方式
     *
     * @param userId 用户id
     * @return 用户可进行的认证方式列表
     */
    List<Integer> selectAuthenticationMethod(String userId);


    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息ø
     */
    UserInfo getCurrentUserInfo();

    /**
     * 获取当前登录用户的身份id
     *
     * @return 身份idø
     */
    String getCurrentUserIdentityId();

    /**
     * 校验当前用户是否已经认证
     *
     * @return 是否认证
     */
    Boolean verifyWhetherAuthenticated();
}
