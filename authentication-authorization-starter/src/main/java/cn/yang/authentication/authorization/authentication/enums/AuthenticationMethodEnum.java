package cn.yang.authentication.authorization.authentication.enums;

/**
 * 认证方式
 *
 * @author : 未见清海
 */
public enum AuthenticationMethodEnum {

    PASSWORD(0, "账号密码登录认证"),

    PHONE(1, "手机号短信认证"),

    ;

    /**
     * 认证方式
     */
    private final Integer method;

    /**
     * 描述
     */
    private final String description;

    AuthenticationMethodEnum(Integer method, String description) {
        this.method = method;
        this.description = description;
    }

    public Integer getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }
}
