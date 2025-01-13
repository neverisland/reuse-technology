package cn.yang.common.data.structure.vo.processor;

import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.exception.HandlerNotFoundException;
import cn.yang.common.data.structure.exception.IllegalDataException;
import cn.yang.common.data.structure.vo.result.ResultVo;

import java.util.Objects;

/**
 * 用于对Result进行快速拆包处理
 *
 * @author 未见清海
 */
public class ResultProcessor<T> {

    private final ResultVo<T> result;
    private SuccessHandler<T> successHandler;
    private FailureHandler<T> failureHandler;
    private ErrorHandler<T> errorHandler;

    /**
     * 构造函数，接受 Result 实例
     */
    public ResultProcessor(ResultVo<T> result) {

        if (Objects.isNull(result)) {
            throw new IllegalDataException("ResultVo must not be null");
        }
        this.result = result;
    }

    /**
     * 注册成功状态的处理器
     * <br/>
     * 成功状态处理器必须注册。
     *
     * @param handler 成功状态处理器
     * @return 返回当前对象以支持链式调用
     */
    public ResultProcessor<T> onSuccess(SuccessHandler<T> handler) {
        successHandler = handler;
        return this;
    }

    /**
     * 注册失败状态的处理器
     * <br/>
     * 失败状态处理器必须注册，
     *
     * @param handler 失败状态处理器
     * @return 返回当前对象以支持链式调用
     */
    public ResultProcessor<T> onFailure(FailureHandler<T> handler) {
        failureHandler = handler;
        return this;
    }

    /**
     * 注册异常处理器
     * <br/>
     * 异常处理器可以不注册，
     * 当异常处理器不存在时，会将异常向外抛出。
     *
     * @param handler 异常处理器
     * @return 返回当前对象以支持链式调用
     */
    public ResultProcessor<T> onError(ErrorHandler<T> handler) {
        errorHandler = handler;
        return this;
    }

    /**
     * 通知所有处理器
     */
    public T processResult() {

        if (Objects.isNull(successHandler)) {
            throw new HandlerNotFoundException("success handler not found.");
        }
        if (Objects.isNull(failureHandler)) {
            throw new HandlerNotFoundException("failure handler not found.");
        }

        try {
            Integer statusCode = result.getCode();
            if (StatusCodeEnum.SUCCESS.equalTo(statusCode)) {

                return successHandler.handle(result.getCode(), result.getCodeMsg(), result.getDetails(), result.getData());
            } else {

                return failureHandler.handle(result.getCode(), result.getCodeMsg(), result.getDetails());
            }
        } catch (Exception e) {

            if (Objects.nonNull(errorHandler)) {
                return errorHandler.handleError(e);
            } else {
                // 如果没有注册错误处理器，则重新抛出异常
                throw e;
            }
        }
    }

    /**
     * 判断结果状态
     *
     * @return true-成功；false-失败
     */
    public boolean checkResult() {

        Integer statusCode = result.getCode();
        return StatusCodeEnum.SUCCESS.getCode().equals(statusCode);
    }

    /**
     * 失败处理
     * <br/>
     * 仅当checkResult结果为false时使用。
     *
     * @param failureProcess 失败处理器
     */
    public void failureProcess(FailureProcess<T> failureProcess) {
        failureProcess.process(result.getCode(), result.getCodeMsg(), result.getDetails(), result.getData());
    }

}
