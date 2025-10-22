package cn.yang.authentication.authorization.user.dto.permission;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限详情
 *
 * @author : 未见清海
 */
@Data
public class PermissionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8526806580672472424L;

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
