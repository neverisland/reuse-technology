package cn.yang.authentication.authorization.user.dal;

import cn.yang.common.data.structure.entity.BaseTimeEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限
 *
 * @author : 未见清海
 */
@Data
public class PermissionDo extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1399888753807019456L;

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
