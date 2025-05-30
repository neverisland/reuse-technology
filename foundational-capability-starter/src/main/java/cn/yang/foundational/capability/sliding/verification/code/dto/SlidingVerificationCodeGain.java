package cn.yang.foundational.capability.sliding.verification.code.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 滑动验证码获取
 */
@Data
public class SlidingVerificationCodeGain implements Serializable {

    @Serial
    private static final long serialVersionUID = 8045296565832120317L;

    /**
     * 背景图片宽
     */
    private Integer backgroundWidth;

    /**
     * 背景图片高
     */
    private Integer backgroundHeight;

    /**
     * 验证图片宽
     */
    private Integer verifyWidth;

    /**
     * 验证图片高
     */
    private Integer verifyHeight;

    /**
     * 验证图片突出块半径
     */
    private Integer verifyRadius;
}
