package cn.yang.foundational.capability.sliding.verification.code.dto;

import lombok.Data;

import java.io.Serial;

/**
 * 滑动验证码的返回数据
 */
@Data
public class SlidingVerificationCodeReturn extends SlidingVerificationCodeBaseReturn {

    @Serial
    private static final long serialVersionUID = 3307335025306224759L;

    /**
     * 滑动距离
     */
    private Integer slideDistance;

}
