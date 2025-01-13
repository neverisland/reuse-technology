package cn.yang.common.data.structure.vo.processor;

/**
 * 失败处理器
 *
 * @author : 未见清海
 */
@FunctionalInterface
public interface FailureHandler<T> {

    /**
     * 请求失败处理器
     *
     * @param code    状态码
     * @param codeMsg 状态码描述
     * @param details 用于详细的描述错误信息
     */
    T handle(Integer code, String codeMsg, String details);
}
