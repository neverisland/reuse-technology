package cn.yang.common.data.structure.vo.result;

/**
 * 返回对象生成器
 *
 * @author : 未见清海
 */
public class ResultFactory {

    private ResultFactory() {
    }

    public static ResultVo<?> success(ResultCodeInterface resultCodeInterface) {

        return new ResultVo<>(resultCodeInterface);
    }

    public static ResultVo<?> success(ResultCodeInterface resultCodeInterface, String details) {

        return new ResultVo<>(resultCodeInterface, details);
    }

    public static <T> ResultVo<T> success(ResultCodeInterface resultCodeInterface, T t) {

        return new ResultVo<>(resultCodeInterface, t);
    }

    public static <T> ResultVo<T> success(ResultCodeInterface resultCodeInterface, String details, T t) {

        return new ResultVo<>(resultCodeInterface, details, t);
    }

    public static ResultVo<?> failure(ResultCodeInterface resultCodeInterface) {

        return new ResultVo<>(resultCodeInterface);
    }


    public static ResultVo<?> failure(ResultCodeInterface resultCodeInterface, String details) {

        return new ResultVo<>(resultCodeInterface, details);
    }

}
