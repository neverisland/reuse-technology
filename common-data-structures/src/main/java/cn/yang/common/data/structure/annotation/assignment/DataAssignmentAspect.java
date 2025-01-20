package cn.yang.common.data.structure.annotation.assignment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 基础数据赋值切面
 *
 * @author : 未见清海
 */
@Aspect
@Component
public class DataAssignmentAspect {

    @Around("@annotation(BaseDataAssignment)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();  // 记录开始时间

        // 执行被增强的方法
        Object proceed = joinPoint.proceed();

        long end = System.currentTimeMillis();  // 记录结束时间
        System.out.println(joinPoint.getSignature() + " executed in " + (end - start) + "ms");

        return proceed;
    }
}
