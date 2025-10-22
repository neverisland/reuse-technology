package cn.yang.authentication.authorization.user.enums;

import lombok.Getter;

/**
 * 身份类型枚举
 * 身份类型 0 - 系统用户， 1 - 普通用户
 *
 * @author : 未见清海
 */
@Getter
public enum IdentityTypeEnum {

    SYSTEM_IDENTITY(0, "系统身份"),

    ORDINARY_IDENTITY(1, "普通身份"),
    ;

    /**
     * 身份类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;


    IdentityTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

}
