package cn.yang.common.data.structure.vo.result;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回结果封装
 *
 * @author : 未见清海
 */
public class ResultVo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5680953244770051799L;

    /**
     * 状态码
     * 数据由 StatusEnum 指定
     * -1 未知
     * 0 表示成功
     * <1 表示失败
     */
    private Integer code;

    /**
     * 状态码描述
     */
    private String codeMsg;

    /**
     * 可选
     * 详细描述
     * 用于详细的描述错误信息
     */
    private String details = "";

    /**
     * 可选
     * 目标数据
     */
    private T data = null;

    public ResultVo(ResultCodeInterface resultCodeInterface) {
        this.code = resultCodeInterface.getCode();
        this.codeMsg = resultCodeInterface.getCodeMsg();
    }

    public ResultVo(ResultCodeInterface resultCodeInterface, String details) {
        this.code = resultCodeInterface.getCode();
        this.codeMsg = resultCodeInterface.getCodeMsg();
        this.details = details;
    }

    public ResultVo(ResultCodeInterface resultCodeInterface, T data) {
        this.code = resultCodeInterface.getCode();
        this.codeMsg = resultCodeInterface.getCodeMsg();
        this.data = data;
    }

    public ResultVo(ResultCodeInterface resultCodeInterface, String details, T data) {
        this.code = resultCodeInterface.getCode();
        this.codeMsg = resultCodeInterface.getCodeMsg();
        this.details = details;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" + "code=" + code + ", codeMsg='" + codeMsg + '\'' + ", details='" + details + '\'' + ", data=" + data + '}';
    }
}
