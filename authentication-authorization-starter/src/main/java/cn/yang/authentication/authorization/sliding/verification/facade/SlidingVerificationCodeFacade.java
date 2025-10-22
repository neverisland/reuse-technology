package cn.yang.authentication.authorization.sliding.verification.facade;

import cn.yang.authentication.authorization.sliding.verification.dto.SlidingVerificationCodeDto;
import cn.yang.authentication.authorization.sliding.verification.enums.SlidingVerificationCodeBusinessEnum;
import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeGain;

/**
 * 滑动验证码业务接口
 *
 * @author : 未见清海
 */
public interface SlidingVerificationCodeFacade {

    /**
     * 滑动验证码生成
     *
     * @param slidingVerificationCodeGain         生成入参
     * @param slidingVerificationCodeBusinessEnum 滑动验证码业务
     * @return 生成结果
     */
    SlidingVerificationCodeDto slideCaptchaGen(SlidingVerificationCodeGain slidingVerificationCodeGain,
                                               SlidingVerificationCodeBusinessEnum slidingVerificationCodeBusinessEnum);

    /**
     * 校验滑动验证码是否正确,Code只校验一次
     *
     * @param userSlideDistance                   用户滑动的距离
     * @param cacheSlidingVerificationCode        缓存中的滑动验证码code
     * @param slidingVerificationCodeBusinessEnum 滑动验证码业务
     * @return 是否通过
     */
    Boolean slideCaptchaCheck(Integer userSlideDistance, String cacheSlidingVerificationCode, SlidingVerificationCodeBusinessEnum slidingVerificationCodeBusinessEnum);

}
