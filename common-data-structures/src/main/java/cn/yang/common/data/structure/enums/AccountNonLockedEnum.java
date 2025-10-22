package cn.yang.common.data.structure.enums;

import lombok.Getter;

/**
 * 是否未锁定枚举
 *
 * @author : 未见清海
 */
@Getter
public enum AccountNonLockedEnum {

    ENABLE(Boolean.TRUE, "未锁定"),

    DISABLE(Boolean.FALSE, "锁定"),
    ;

    /**
     * 锁定状态
     */
    private final Boolean enabled;

    /**
     * 描述
     */
    private final String description;

    AccountNonLockedEnum(Boolean enabled, String description) {
        this.enabled = enabled;
        this.description = description;
    }

}
