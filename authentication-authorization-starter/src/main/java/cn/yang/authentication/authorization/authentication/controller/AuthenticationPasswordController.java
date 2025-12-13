package cn.yang.authentication.authorization.authentication.controller;

import cn.yang.authentication.authorization.authentication.dto.PasswordLoginDto;
import cn.yang.authentication.authorization.authentication.dto.ResetPasswordDto;
import cn.yang.authentication.authorization.authentication.facade.AuthenticationPasswordFacade;
import cn.yang.authentication.authorization.authentication.facade.AuthorizationFacade;
import cn.yang.authentication.authorization.sliding.verification.enums.SlidingVerificationCodeBusinessEnum;
import cn.yang.authentication.authorization.sliding.verification.facade.SlidingVerificationCodeFacade;
import cn.yang.authentication.authorization.enums.ErrorStatusCodeEnum;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号密码登录认证接口层
 *
 * @author : 未见清海
 */
@RestController
@RequestMapping("/authentication/password")
public class AuthenticationPasswordController {

    @Resource
    private AuthenticationPasswordFacade authenticationPasswordFacade;

    @Resource
    private AuthorizationFacade authorizationFacade;

    @Resource
    private SlidingVerificationCodeFacade slidingVerificationCodeFacade;

    /**
     * 账号密码登录
     *
     * @return 认证标识
     */
    @PostMapping("/login")
    public ResultVo<String> passwordLogin(@RequestBody @Validated PasswordLoginDto passwordLoginDto) {
        // 校验滑动验证码是否通过
        Boolean slideCaptchaCheck = slidingVerificationCodeFacade.slideCaptchaCheck(passwordLoginDto.getUserSlideDistance(),
                passwordLoginDto.getCacheSlidingVerificationCode(),
                SlidingVerificationCodeBusinessEnum.PASSWORD_LOGIN);
        if (!slideCaptchaCheck) {
            throw new BusinessException(ErrorStatusCodeEnum.SLIDE_CAPTCHA_CHECK_ERROR, "验证失败，请控制拼图对齐缺口");
        }
        // 获取可以登录的身份id
        String identityId = authenticationPasswordFacade.passwordLogin(passwordLoginDto);
        // 对用户id授权操作
        String authorizationCode = authorizationFacade.authorizationByIdentityId(identityId);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "登录成功", authorizationCode);
    }

    /**
     * 重置用户密码
     *
     * @param resetPasswordDto 重置密码入参
     * @return 是否成功响应
     */
    @PostMapping("/resetPassword")
    public ResultVo<?> resetPassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        authenticationPasswordFacade.resetPassword(resetPasswordDto);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "重置成功");
    }
}
