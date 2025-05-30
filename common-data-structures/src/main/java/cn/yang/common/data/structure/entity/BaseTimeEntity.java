package cn.yang.common.data.structure.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础时间实体
 *
 * @author : 未见清海
 */
@Data
public class BaseTimeEntity {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
