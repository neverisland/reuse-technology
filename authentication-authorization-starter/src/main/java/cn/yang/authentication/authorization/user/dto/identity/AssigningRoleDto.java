package cn.yang.authentication.authorization.user.dto.identity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分配角色的入参
 * @author : 未见清海
 */
@Data
public class AssigningRoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 113321451041696501L;

    /**
     * 身份id
     */
    @NotNull(message = "身份id不能为空")
    private String identityId;

    /**
     * 分配的角色id列表
     */
    @NotEmpty(message = "分配的角色列表不能为空")
    private List<String> roleIdList;

}
