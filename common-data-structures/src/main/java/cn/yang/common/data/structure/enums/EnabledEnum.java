package cn.yang.common.data.structure.enums;

import lombok.Getter;

/**
 * 启用禁用枚举
 *
 * @author : 未见清海
 */
@Getter
public enum EnabledEnum {

    ENABLE(Boolean.TRUE, "启用"),

    DISABLE(Boolean.FALSE, "禁用"),
    ;

    /**
     * 启用禁用状态
     */
    private final Boolean enabled;

    /**
     * 描述
     */
    private final String description;

    EnabledEnum(Boolean enabled, String description) {
        this.enabled = enabled;
        this.description = description;
    }

}
