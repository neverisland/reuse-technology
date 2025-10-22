package cn.yang.authentication.authorization.authentication.service;

import cn.yang.authentication.authorization.authentication.facade.AuthenticationFacade;
import cn.yang.common.data.structure.annotation.assignment.CurrentlyUserFacade;
import cn.yang.common.data.structure.exception.NotLoginException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 获取当前登录用户身份id
 *
 * @author : 未见清海
 */
@Service
public class CurrentlyUserService implements CurrentlyUserFacade {

    @Resource
    private AuthenticationFacade authenticationFacade;

    /**
     * 获取当前登录用户的身份id
     *
     * @return 身份id
     * @throws NotLoginException 用户未登录异常
     */
    @Override
    public String getCurrentlyUserId() throws NotLoginException {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        if (context == null) {
            throw new NotLoginException("用户未登录");
        }

        if (!authenticationFacade.verifyWhetherAuthenticated()) {
            throw new NotLoginException("用户未登录");
        }
        return authenticationFacade.getCurrentUserIdentityId();
    }

}
