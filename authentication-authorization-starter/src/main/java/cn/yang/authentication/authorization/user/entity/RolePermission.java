package cn.yang.authentication.authorization.user.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色权限关联
 *
 * @author 未见清海
 */
@Data
public class RolePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -2569025134213508465L;

    /**
     * 权限角色id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;

    public RolePermission(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

}
