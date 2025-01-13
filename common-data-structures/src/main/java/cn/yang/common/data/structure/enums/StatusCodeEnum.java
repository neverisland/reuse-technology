package cn.yang.common.data.structure.enums;

import cn.yang.common.data.structure.vo.result.ResultCodeInterface;

/**
 * 返回状态枚举
 *
 * @author : 未见清海
 */
public enum StatusCodeEnum implements ResultCodeInterface {


    SUCCESS(0, "成功"),

    UN_KNOW(-1, "未知"),
    ;


    /**
     * 状态码
     */
    private final Integer statusCode;

    /**
     * 描述
     */
    private final String description;

    StatusCodeEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    @Override
    public Integer getCode() {
        return statusCode;
    }

    /**
     * 获取状态码描述
     *
     * @return 状态码描述
     */
    @Override
    public String getCodeMsg() {
        return description;
    }

    /**
     * 是否相等
     *
     * @param status 状态码
     * @return 相同为true, 相反为false
     */
    public boolean equalTo(Integer status) {

        return this.statusCode.equals(status);
    }
}
