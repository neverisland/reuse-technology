package cn.yang.authentication.authorization.sliding.verification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 滑动验证码校验入参
 *
 * @author : 未见清海
 */
public class SlideCaptchaCheckDto {

    /**
     * 用户滑动的距离
     */
    @NotNull(message = "滑动距离不能为空")
    private Integer userSlideDistance;

    /**
     * 缓存中的滑动验证码code
     */
    @NotBlank(message = "滑动验证码Code不能为空")
    private String cacheSlidingVerificationCode;

    public Integer getUserSlideDistance() {
        return userSlideDistance;
    }

    public void setUserSlideDistance(Integer userSlideDistance) {
        this.userSlideDistance = userSlideDistance;
    }

    public String getCacheSlidingVerificationCode() {
        return cacheSlidingVerificationCode;
    }

    public void setCacheSlidingVerificationCode(String cacheSlidingVerificationCode) {
        this.cacheSlidingVerificationCode = cacheSlidingVerificationCode;
    }

    @Override
    public String toString() {
        return "SlideCaptchaCheckDto{" +
                "userSlideDistance=" + userSlideDistance +
                ", cacheSlidingVerificationCode='" + cacheSlidingVerificationCode + '\'' +
                '}';
    }
}
