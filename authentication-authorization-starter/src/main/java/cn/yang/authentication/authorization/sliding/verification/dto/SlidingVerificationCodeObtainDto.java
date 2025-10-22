package cn.yang.authentication.authorization.sliding.verification.dto;

import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeGain;

/**
 * 滑动验证码获取的入参
 *
 * @author : 未见清海
 */
public class SlidingVerificationCodeObtainDto extends SlidingVerificationCodeGain {

    /**
     * 业务标识
     */
    private String business;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "SlidingVerificationCodeObtainDto{" +
                "business='" + business + '\'' +
                "} " + super.toString();
    }
}
