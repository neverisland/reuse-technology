package cn.yang.foundational.capability.sliding.verification.code.dto;

/**
 * 滑动验证码出参基础数据
 */
public class SlidingVerificationCodeBaseReturn {

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

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getVerifyImg() {
        return verifyImg;
    }

    public void setVerifyImg(String verifyImg) {
        this.verifyImg = verifyImg;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "SlidingVerificationCodeBaseReturn{" +
                "backgroundImg='" + backgroundImg + '\'' +
                ", verifyImg='" + verifyImg + '\'' +
                ", height=" + height +
                '}';
    }
}
