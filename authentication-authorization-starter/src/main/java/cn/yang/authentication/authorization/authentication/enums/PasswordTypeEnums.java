package cn.yang.authentication.authorization.authentication.enums;

/**
 * 用户密码类型
 * 0-初始密码 1-自定义密码
 *
 * @author : 未见清海
 */
public enum PasswordTypeEnums {

    INITIAL_PASSWORD(0, "初始密码"),

    CUSTOM_PASSWORD(1, "自定义密码"),
    ;

    /**
     * 密码类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

    PasswordTypeEnums(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
