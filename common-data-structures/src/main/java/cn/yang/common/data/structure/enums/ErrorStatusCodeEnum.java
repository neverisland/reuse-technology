package cn.yang.common.data.structure.enums;

import cn.yang.common.data.structure.vo.result.ResultCodeInterface;

/**
 * 异常返回状态枚举
 *
 * @author : 未见清海
 */
public enum ErrorStatusCodeEnum implements ResultCodeInterface {

    PARAMETER_VERIFICATION_EXCEPTION(1, "参数校验异常"),

    DATA_DOES_NOT_EXIST(2, "数据不存在"),

    DATA_CANNOT_BE_INSERT(3, "数据不允许新增"),

    DATA_CANNOT_BE_UPDATED(4, "数据不允许修改"),

    ABNORMAL_OPERATION(5, "操作异常"),

    SLIDE_CAPTCHA_CHECK_ERROR(6, "滑动验证码校验失败"),

    AUTHENTICATION_ERROR(7, "认证失败"),

    NOT_LOGIN_EXCEPTION(8, "用户未登录"),

    PERMISSION_ERROR(9, "权限校验异常"),
    
    ;


    /**
     * 返回状态码
     */
    private final Integer statusCode;

    /**
     * 描述
     */
    private final String description;

    ErrorStatusCodeEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return statusCode;
    }

    @Override
    public String getCodeMsg() {
        return description;
    }
}
