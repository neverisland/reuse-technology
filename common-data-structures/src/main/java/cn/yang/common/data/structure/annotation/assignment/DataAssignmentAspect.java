package cn.yang.common.data.structure.annotation.assignment;

import cn.yang.common.data.structure.entity.BaseEntity;
import cn.yang.common.data.structure.entity.BaseTimeEntity;
import cn.yang.common.data.structure.exception.NotLoginException;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础数据赋值切面
 *
 * @author : 未见清海
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "data.assignment", name = "enable")
public class DataAssignmentAspect {

    @Resource
    private CurrentlyUserFacade currentlyUserFacade;

    @Before("@annotation(baseDataAssignment)")
    public void beforeMethod(JoinPoint joinPoint, BaseDataAssignment baseDataAssignment) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof List<?> list) {
                // 列表数据类型
                if (!list.isEmpty() && list.get(0) instanceof BaseTimeEntity) {
                    LocalDateTime now = LocalDateTime.now();
                    for (Object o : list) {
                        assert o instanceof BaseTimeEntity;
                        BaseTimeEntity baseTimeEntity = (BaseTimeEntity) o;
                        dataHandle(baseTimeEntity, now, baseDataAssignment.value());
                    }
                }
            }
            // 对象数据类型
            if (arg instanceof BaseTimeEntity baseTimeEntity) {
                LocalDateTime now = LocalDateTime.now();
                dataHandle(baseTimeEntity, now, baseDataAssignment.value());
            }
        }
    }

    /**
     * 处理入参数据
     *
     * @param baseTimeEntity        基础时间实体
     * @param now                   当前时间
     * @param dataOperationTypeEnum 数据操作枚举
     */
    private void dataHandle(BaseTimeEntity baseTimeEntity, LocalDateTime now, DataOperationTypeEnum dataOperationTypeEnum) {
        if (dataOperationTypeEnum == DataOperationTypeEnum.CREATE) {
            baseTimeEntity.setCreateTime(now);
            baseTimeEntity.setUpdateTime(now);
            if (baseTimeEntity instanceof BaseEntity) {
                try {
                    ((BaseEntity) baseTimeEntity).setCreateUserId(currentlyUserFacade.getCurrentlyUserId());
                    ((BaseEntity) baseTimeEntity).setUpdateUserId(currentlyUserFacade.getCurrentlyUserId());
                } catch (NotLoginException loginException) {
                    ((BaseEntity) baseTimeEntity).setCreateUserId("");
                    ((BaseEntity) baseTimeEntity).setUpdateUserId("");
                }
            }
        }
        if (dataOperationTypeEnum == DataOperationTypeEnum.UPDATE) {
            baseTimeEntity.setUpdateTime(now);
            if (baseTimeEntity instanceof BaseEntity) {
                try {
                    ((BaseEntity) baseTimeEntity).setUpdateUserId(currentlyUserFacade.getCurrentlyUserId());
                } catch (NotLoginException loginException) {
                    ((BaseEntity) baseTimeEntity).setUpdateUserId("");
                }
            }
        }
    }
}
