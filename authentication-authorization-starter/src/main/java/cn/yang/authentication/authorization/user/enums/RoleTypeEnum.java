package cn.yang.authentication.authorization.user.enums;

import lombok.Getter;

/**
 * 角色类型枚举
 *
 * @author : 未见清海
 */
@Getter
public enum RoleTypeEnum {

    BUILT_IN_ROLE(0, "内置角色"),

    CUSTOMIZE_ROLE(1, "自定义角色"),
    ;

    /**
     * 角色类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

    RoleTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

}
