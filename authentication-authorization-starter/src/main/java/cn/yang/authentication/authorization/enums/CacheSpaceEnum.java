package cn.yang.authentication.authorization.enums;

import lombok.Getter;

/**
 * 自定义的缓存空间定义
 *
 * @author : 未见清海
 */
@Getter
public enum CacheSpaceEnum {

    SLIDING_VERIFICATION_CODE("sliding-verification-code", "滑动验证码"),

    ;

    /**
     * 缓存标识
     */
    private final String mark;

    /**
     * 描述
     */
    private final String description;

    CacheSpaceEnum(String mark, String description) {
        this.mark = mark;
        this.description = description;
    }

}
