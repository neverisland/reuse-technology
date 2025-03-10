package cn.yang.foundational.capability.sliding.verification.code.dto;

/**
 * 滑动验证码获取
 */
public class SlidingVerificationCodeGain {

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

    public Integer getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundWidth(Integer backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    public Integer getBackgroundHeight() {
        return backgroundHeight;
    }

    public void setBackgroundHeight(Integer backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }

    public Integer getVerifyWidth() {
        return verifyWidth;
    }

    public void setVerifyWidth(Integer verifyWidth) {
        this.verifyWidth = verifyWidth;
    }

    public Integer getVerifyHeight() {
        return verifyHeight;
    }

    public void setVerifyHeight(Integer verifyHeight) {
        this.verifyHeight = verifyHeight;
    }

    public Integer getVerifyRadius() {
        return verifyRadius;
    }

    public void setVerifyRadius(Integer verifyRadius) {
        this.verifyRadius = verifyRadius;
    }

    @Override
    public String toString() {
        return "SlidingVerificationCodeGain{" +
                "backgroundWidth=" + backgroundWidth +
                ", backgroundHeight=" + backgroundHeight +
                ", verifyWidth=" + verifyWidth +
                ", verifyHeight=" + verifyHeight +
                ", verifyRadius=" + verifyRadius +
                '}';
    }
}
