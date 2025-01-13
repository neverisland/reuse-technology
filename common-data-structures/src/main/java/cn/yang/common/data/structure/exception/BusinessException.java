package cn.yang.common.data.structure.exception;

import cn.yang.common.data.structure.vo.result.ResultCodeInterface;

import java.io.Serial;
import java.io.Serializable;

/**
 * 业务异常
 *
 * @author : 未见清海
 */
public class BusinessException extends RuntimeException implements Serializable, ResultCodeInterface {

    @Serial
    private static final long serialVersionUID = -7818284315551187765L;

    /**
     * 返回状态码
     */
    private final ResultCodeInterface resultCodeInterface;

    /**
     * 子状态异常的详细描述
     */
    protected final String details;

    public BusinessException(ResultCodeInterface resultCodeInterface) {
        this.resultCodeInterface = resultCodeInterface;
        this.details = "";
    }

    public BusinessException(ResultCodeInterface resultCodeInterface, String details) {
        this.resultCodeInterface = resultCodeInterface;
        this.details = details;
    }

    public BusinessException(ResultCodeInterface resultCodeInterface, String details, Throwable cause) {
        super(cause);
        this.resultCodeInterface = resultCodeInterface;
        this.details = details;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    @Override
    public Integer getCode() {
        return resultCodeInterface.getCode();
    }

    /**
     * 获取状态码描述
     *
     * @return 状态码描述
     */
    @Override
    public String getCodeMsg() {
        return resultCodeInterface.getCodeMsg();
    }
}
