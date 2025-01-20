package cn.yang.common.data.structure.annotation.assignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础数据赋值枚举
 *
 * @author : 未见清海
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseDataAssignment {

    DataOperationTypeEnum value() default DataOperationTypeEnum.CREATE;

}
