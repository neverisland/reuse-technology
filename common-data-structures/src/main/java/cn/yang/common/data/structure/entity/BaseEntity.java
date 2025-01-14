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
    private String createUserId;

    /**
     * 更新者id
     */
    private String updateUserId;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createUserId='" + createUserId + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                "} " + super.toString();
    }
}
