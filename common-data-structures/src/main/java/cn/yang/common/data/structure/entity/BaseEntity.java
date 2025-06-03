package cn.yang.common.data.structure.entity;

import lombok.Data;

/**
 * 基础业务实体
 *
 * @author : 未见清海
 */
@Data
public class BaseEntity extends BaseTimeEntity {

    /**
     * 创建者id
     */
    private String createUserId;

    /**
     * 更新者id
     */
    private String updateUserId;

}
