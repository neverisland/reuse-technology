package cn.yang.foundational.capability.sliding.verification.code.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 滑动验证码出参基础数据
 */
@Data
public class SlidingVerificationCodeBaseReturn implements Serializable {

    @Serial
    private static final long serialVersionUID = -6278556479686229731L;

    /**
     * 背景图片base64
     */
    private String backgroundImg;

    /**
     * 验证图片base64
     */
    private String verifyImg;

    /**
     * 验证图片所处底部高度
     */
    private Integer height;

}
