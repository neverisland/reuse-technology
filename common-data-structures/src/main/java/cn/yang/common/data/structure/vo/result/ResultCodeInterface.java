package cn.yang.common.data.structure.vo.result;

/**
 * 返回数据枚举接口定义
 *
 * @author : 未见清海
 */
public interface ResultCodeInterface {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取状态码描述
     *
     * @return 状态码描述
     */
    String getCodeMsg();
}
