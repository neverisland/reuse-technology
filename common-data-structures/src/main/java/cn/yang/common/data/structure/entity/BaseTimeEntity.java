package cn.yang.common.data.structure.entity;

import java.time.LocalDateTime;

/**
 * 基础时间实体
 *
 * @author : 未见清海
 */
public class BaseTimeEntity {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BaseTimeEntity{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
