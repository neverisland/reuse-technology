package cn.yang.authentication.authorization.user.dal;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 身份角色关联
 *
 * @author 未见清海
 */
@Data
public class IdentityRoleDo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1371850690135443721L;

    /**
     * 身份角色关联id
     */
    private String id;

    /**
     * 身份id
     */
    private String identityId;

    /**
     * 角色id
     */
    private String roleId;
}
