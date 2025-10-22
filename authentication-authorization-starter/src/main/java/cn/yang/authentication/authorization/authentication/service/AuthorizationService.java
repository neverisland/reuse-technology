package cn.yang.authentication.authorization.authentication.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.yang.authentication.authorization.authentication.facade.AuthorizationFacade;
import cn.yang.authentication.authorization.user.entity.Identity;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.UserFacade;
import cn.yang.common.data.structure.enums.EnabledEnum;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.enums.ErrorStatusCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 授权业务层
 *
 * @author : 未见清海
 */
@Service
public class AuthorizationService implements AuthorizationFacade {

    @Resource
    private IdentityFacade identityFacade;

    @Resource
    private UserFacade userFacade;

    /**
     * 根据用户id获取可以授权的身份
     *
     * @param userId 用户id
     * @return 可以授权的身份id
     */
    @Override
    public String getAuthorizedIdentity(String userId) {
        // 校验用户是否正常
        User user = userFacade.selectById(userId);
        if (!EnabledEnum.ENABLE.getEnabled().equals(user.getEnabled())) {
            throw new BusinessException(ErrorStatusCodeEnum.AUTHENTICATION_ERROR, "用户已禁用,登录失败");
        }
        if (!AccountNonLockedEnum.ENABLE.getEnabled().equals(user.getAccountNonLocked())) {
            throw new BusinessException(ErrorStatusCodeEnum.AUTHENTICATION_ERROR, "用户已锁定,请稍后再试");
        }
        // 获取用户的身份数据
        List<Identity> identityList = identityFacade.selectByUserId(userId);
        // todo 获取合适的身份数据，设置默认等等
        return identityList.get(0).getId();
    }

    /**
     * 授权根据身份id
     *
     * @param identityId 身份id
     * @return 授权标识
     */
    @Override
    public String authorizationByIdentityId(String identityId) {
        StpUtil.login(identityId);
        return StpUtil.getTokenValue();
    }
}
