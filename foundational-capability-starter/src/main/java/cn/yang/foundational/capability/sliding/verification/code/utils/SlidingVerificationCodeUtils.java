package cn.yang.foundational.capability.sliding.verification.code.utils;

import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeGain;
import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeReturn;
import cn.yang.foundational.capability.sliding.verification.code.exception.SlidingVerificationCodeException;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * 滑动验证码工具类
 */
public class SlidingVerificationCodeUtils {

    /**
     * 拼图验证码允许偏差
     **/
    private static final Integer ALLOW_DEVIATION = 8;


    private SlidingVerificationCodeUtils() {

    }

    /**
     * 滑动验证码生成
     *
     * @param slidingVerificationCodeGain 生成入参
     * @return 生成结果
     */
    public static SlidingVerificationCodeReturn slideCaptchaGen(SlidingVerificationCodeGain slidingVerificationCodeGain) throws SlidingVerificationCodeException {
        if (Objects.isNull(slidingVerificationCodeGain) || Objects.isNull(slidingVerificationCodeGain.getBackgroundHeight()) || Objects.isNull(slidingVerificationCodeGain.getBackgroundWidth())
                || Objects.isNull(slidingVerificationCodeGain.getVerifyHeight()) || Objects.isNull(slidingVerificationCodeGain.getVerifyWidth()) || Objects.isNull(slidingVerificationCodeGain.getVerifyRadius())) {
            throw new SlidingVerificationCodeException("获取滑动验证码参数不全");
        }
        //获取阻塞块的宽高/半径
        int blockRadius = 9;
        //获取资源图
        BufferedImage canvasImage = CaptchaUtils.getBufferedImage();
        //调整原图到指定大小
        canvasImage = CaptchaUtils.imageResize(canvasImage, slidingVerificationCodeGain.getBackgroundWidth(), slidingVerificationCodeGain.getBackgroundHeight());
        //随机生成阻塞块坐标
        int blockX = CaptchaUtils.getNonceByRange(slidingVerificationCodeGain.getVerifyWidth(), slidingVerificationCodeGain.getBackgroundWidth() - slidingVerificationCodeGain.getVerifyWidth() - 10);
        int blockY = CaptchaUtils.getNonceByRange(10, slidingVerificationCodeGain.getBackgroundHeight() - slidingVerificationCodeGain.getVerifyHeight() + 1);
        //阻塞块
        BufferedImage blockImage = new BufferedImage(slidingVerificationCodeGain.getVerifyWidth(), slidingVerificationCodeGain.getVerifyHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        //新建的图像根据轮廓图颜色赋值，源图生成遮罩
        CaptchaUtils.cutByTemplate(canvasImage, blockImage, slidingVerificationCodeGain.getVerifyWidth(), slidingVerificationCodeGain.getVerifyHeight(), blockRadius, blockX, blockY);
        //设置返回参数
        SlidingVerificationCodeReturn slidingVerificationCodeReturn = new SlidingVerificationCodeReturn();
        slidingVerificationCodeReturn.setSlideDistance(blockX);
        slidingVerificationCodeReturn.setHeight(blockY);
        slidingVerificationCodeReturn.setVerifyImg(CaptchaUtils.toBase64(blockImage, "png"));
        slidingVerificationCodeReturn.setBackgroundImg(CaptchaUtils.toBase64(canvasImage, "png"));
        return slidingVerificationCodeReturn;
    }

    /**
     * 校验滑动验证码是否正确
     *
     * @param userSlideDistance  用户滑动的距离
     * @param cacheSlideDistance 实际缓存的距离
     * @return 是否通过
     */
    public static boolean slideCaptchaCheck(Integer userSlideDistance, Integer cacheSlideDistance) throws SlidingVerificationCodeException {
        if (Objects.isNull(userSlideDistance) || Objects.isNull(cacheSlideDistance)) {
            throw new SlidingVerificationCodeException("参数不全");
        }
        // 根据移动距离判断验证是否成功
        return Math.abs(cacheSlideDistance - userSlideDistance) < ALLOW_DEVIATION;
    }
}
