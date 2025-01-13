package cn.yang.common.data.structure.vo.processor;

/**
 * 异常处理器
 *
 * @author : 未见清海
 */
@FunctionalInterface
public interface ErrorHandler<T> {

    /**
     * 异常处理逻辑
     *
     * @param e 异常
     * @return 返回数据
     */
    T handleError(Exception e);
}
