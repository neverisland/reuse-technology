package cn.yang.authentication.authorization.sliding.verification.service;

import cn.yang.authentication.authorization.enums.CacheSpaceEnum;
import cn.yang.authentication.authorization.sliding.verification.dto.SlidingVerificationCodeDto;
import cn.yang.authentication.authorization.sliding.verification.enums.SlidingVerificationCodeBusinessEnum;
import cn.yang.authentication.authorization.sliding.verification.facade.SlidingVerificationCodeFacade;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.authentication.authorization.enums.ErrorStatusCodeEnum;
import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeGain;
import cn.yang.foundational.capability.sliding.verification.code.dto.SlidingVerificationCodeReturn;
import cn.yang.foundational.capability.sliding.verification.code.exception.SlidingVerificationCodeException;
import cn.yang.foundational.capability.sliding.verification.code.utils.SlidingVerificationCodeUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 滑动验证码业务实现
 *
 * @author : 未见清海
 */
@Service
public class SlidingVerificationCodeService implements SlidingVerificationCodeFacade {

    private final static Logger logger = LoggerFactory.getLogger(SlidingVerificationCodeService.class);

    @Resource
    private CacheManager cacheManager;

    /**
     * 滑动验证码生成
     *
     * @param slidingVerificationCodeGain         生成入参
     * @param slidingVerificationCodeBusinessEnum 滑动验证码业务
     * @return 生成结果
     */
    @Override
    public SlidingVerificationCodeDto slideCaptchaGen(SlidingVerificationCodeGain slidingVerificationCodeGain,
                                                      SlidingVerificationCodeBusinessEnum slidingVerificationCodeBusinessEnum) {
        try {
            SlidingVerificationCodeReturn slidingVerificationCodeReturn = SlidingVerificationCodeUtils.slideCaptchaGen(slidingVerificationCodeGain);

            // 存入缓存
            Cache cache = cacheManager.getCache(CacheSpaceEnum.SLIDING_VERIFICATION_CODE.getMark());
            // 生成滑动验证码缓存
            String uuid = UUID.randomUUID().toString();
            assert cache != null;
            cache.put(slidingVerificationCodeBusinessEnum.getBusiness() + ":" + uuid, slidingVerificationCodeReturn.getSlideDistance());

            SlidingVerificationCodeDto slidingVerificationCodeDto = BeanConvertUtils.convert(slidingVerificationCodeReturn, SlidingVerificationCodeDto.class);
            slidingVerificationCodeDto.setCode(uuid);
            return slidingVerificationCodeDto;
        } catch (SlidingVerificationCodeException e) {
            throw new BusinessException(ErrorStatusCodeEnum.ABNORMAL_OPERATION, "获取滑动验证码数据异常");
        }
    }

    /**
     * 校验滑动验证码是否正确,Code只校验一次
     *
     * @param userSlideDistance                   用户滑动的距离
     * @param cacheSlidingVerificationCode        缓存中的滑动验证码code
     * @param slidingVerificationCodeBusinessEnum 滑动验证码业务
     * @return 是否通过
     */
    @Override
    public Boolean slideCaptchaCheck(Integer userSlideDistance,
                                     String cacheSlidingVerificationCode,
                                     SlidingVerificationCodeBusinessEnum slidingVerificationCodeBusinessEnum) {
        try {
            // 获取缓存
            Cache cache = cacheManager.getCache(CacheSpaceEnum.SLIDING_VERIFICATION_CODE.getMark());
            assert cache != null;
            Integer cacheSlideDistance = cache.get(slidingVerificationCodeBusinessEnum.getBusiness() + ":" + cacheSlidingVerificationCode, Integer.class);

            if (cacheSlideDistance == null) {
                logger.warn("缓存中滑动验证码数据获取失败,校验未通过");
                return Boolean.FALSE;
            }

            // 清除缓存数据
            cache.evictIfPresent(slidingVerificationCodeBusinessEnum.getBusiness() + ":" + cacheSlidingVerificationCode);

            return SlidingVerificationCodeUtils.slideCaptchaCheck(userSlideDistance, cacheSlideDistance);
        } catch (SlidingVerificationCodeException e) {
            logger.error("校验滑动验证码异常", e);
            return Boolean.FALSE;
        }
    }
}
