package cn.yang.authentication.authorization.user.dal;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色权限关联
 *
 * @author 未见清海
 */
@Data
public class RolePermissionDo implements Serializable {

    @Serial
    private static final long serialVersionUID = 3658914526340901373L;

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

}
