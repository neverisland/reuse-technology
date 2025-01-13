package cn.yang.common.data.structure.vo.processor;

/**
 * 成功处理器
 *
 * @author : 未见清海
 */
@FunctionalInterface
public interface SuccessHandler<T> {

    /**
     * 请求成功处理器
     *
     * @param code    状态码
     * @param codeMsg 状态码描述
     * @param details 用于详细的描述错误信息
     * @param data    响应数据
     * @return 返回数据
     */
    T handle(Integer code, String codeMsg, String details, T data);
}
