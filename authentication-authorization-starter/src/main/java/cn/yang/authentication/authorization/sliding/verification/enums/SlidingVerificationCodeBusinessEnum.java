package cn.yang.authentication.authorization.sliding.verification.enums;

import cn.yang.common.data.structure.exception.NullDataException;

/**
 * 滑动验证码业务枚举
 *
 * @author : 未见清海
 */
public enum SlidingVerificationCodeBusinessEnum {

    PASSWORD_LOGIN("password_login", "密码登录业务"),
    ;

    /**
     * 滑动验证码业务标识
     */
    private final String business;

    /**
     * 描述
     */
    private final String description;

    SlidingVerificationCodeBusinessEnum(String business, String description) {
        this.business = business;
        this.description = description;
    }

    public String getBusiness() {
        return business;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据业务标识获取滑动验证码业务枚举
     *
     * @param business 业务标识
     * @return 滑动验证码业务枚举
     * @throws NullDataException 空数据异常
     */
    public static SlidingVerificationCodeBusinessEnum selectBusinessEnumByBusiness(String business) throws NullDataException {
        for (SlidingVerificationCodeBusinessEnum slidingVerificationCodeBusinessEnum : values()) {
            if (slidingVerificationCodeBusinessEnum.business.equals(business)) {
                return slidingVerificationCodeBusinessEnum;
            }
        }
        throw new NullDataException("为获取到滑动验证码业务枚举");
    }
}
