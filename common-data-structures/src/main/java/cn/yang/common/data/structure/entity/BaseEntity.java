package cn.yang.common.data.structure.entity;

/**
 * 基础业务实体
 *
 * @author : 未见清海
 */
public class BaseEntity extends BaseTimeEntity {

    /**
     * 创建者id
     */
    private Long createUserId;

    /**
     * 更新者id
     */
    private Long updateUserId;

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createUserId=" + createUserId +
                ", updateUserId=" + updateUserId +
                "} " + super.toString();
    }
}
