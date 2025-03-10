package cn.yang.foundational.capability.sliding.verification.code.dto;

/**
 * 滑动验证码的返回数据
 */
public class SlidingVerificationCodeReturn extends SlidingVerificationCodeBaseReturn {

    /**
     * 滑动距离
     */
    private Integer slideDistance;

    public Integer getSlideDistance() {
        return slideDistance;
    }

    public void setSlideDistance(Integer slideDistance) {
        this.slideDistance = slideDistance;
    }

    @Override
    public String toString() {
        return "SlidingVerificationCodeReturn{" +
                "slideDistance=" + slideDistance +
                "} " + super.toString();
    }
}
