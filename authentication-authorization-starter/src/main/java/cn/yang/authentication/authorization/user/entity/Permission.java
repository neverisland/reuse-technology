package cn.yang.authentication.authorization.user.entity;

import cn.yang.common.data.structure.entity.BaseTimeEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限表
 *
 * @author 未见清海
 */
@Data
public class Permission extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2804107228407508752L;

    /**
     * 权限id
     */
    private String id;

    /**
     * 权限标识
     */
    private String mark;

    /**
     * 权限描述
     */
    private String description;
}
