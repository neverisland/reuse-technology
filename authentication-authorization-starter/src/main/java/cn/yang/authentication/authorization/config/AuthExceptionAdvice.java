package cn.yang.authentication.authorization.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.yang.common.data.structure.enums.ErrorStatusCodeEnum;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常捕获
 *
 * @author : 未见清海
 */
@RestControllerAdvice
public class AuthExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionAdvice.class);

    /**
     * 未登录异常
     *
     * @param e 参数校验异常
     * @return 异常封装返回
     */
    @ExceptionHandler(NotLoginException.class)
    public ResultVo<?> notLoginException(NotLoginException e) {
        logger.warn(e.getMessage());
        return ResultFactory.failure(ErrorStatusCodeEnum.NOT_LOGIN_EXCEPTION, "user not login");
    }

    /**
     * 业务业务异常捕获
     *
     * @param be 业务异常
     * @return 异常封装返回
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultVo<?> baseException(BusinessException be) {
        logger.error("业务异常,", be);
        return ResultFactory.failure(be, be.getDetails());
    }

    /**
     * 参数校验异常捕获
     *
     * @param e 参数校验异常
     * @return 异常封装返回
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVo<?> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        return ResultFactory.failure(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "参数缺失");
    }

    /**
     * 参数校验异常捕获
     *
     * @param e 参数校验异常
     * @return 异常封装返回
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVo<?> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(e.getMessage());
        return ResultFactory.failure(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "参数缺失");
    }

    /**
     * 参数校验框架异常
     *
     * @param e 参数校验异常
     * @return 异常封装返回
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        return ResultFactory.failure(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * 系统异常
     *
     * @param e 参数校验异常
     * @return 异常封装返回
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<?> exception(Exception e) {
        logger.error("系统异常,", e);
        return ResultFactory.failure(StatusCodeEnum.UN_KNOW, "系统异常");
    }
}
