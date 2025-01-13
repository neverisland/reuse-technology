package cn.yang.common.data.structure.utils.bean;


import cn.yang.common.data.structure.exception.IllegalDataException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据转换工具类
 */
public class BeanConvertUtils {


    private BeanConvertUtils() {
    }

    /**
     * 对象转换
     *
     * @param source 转换数据
     * @param target 目标对象类型
     */
    public static <V, T> V convert(T source, Class<V> target) {

        if (source == null) {
            throw new IllegalDataException("source is null.");
        }
        try {
            V vo = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, vo);
            return vo;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new BeanConvertException("bean convert failure.", e);
        }
    }

    /**
     * 对象集合转换
     *
     * @param source 转换数据
     * @param target 目标对象类型
     */
    public static <E, T> List<T> convert(List<E> source, Class<T> target) {

        if (source == null) {
            throw new IllegalDataException("source is null.");
        }

        if (source.isEmpty()) {
            return List.of();
        } else {
            List<T> result = new ArrayList<>(source.size());

            for (E next : source) {
                try {
                    T t = target.getDeclaredConstructor().newInstance();
                    BeanUtils.copyProperties(next, t);
                    result.add(t);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new BeanConvertException("bean convert failure.", e);
                }
            }
            return result;
        }
    }


}
