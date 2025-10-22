package cn.yang.authentication.authorization.user.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 新增角色
 *
 * @author : 未见清海
 */
@Data
public class RoleInsertDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3365631492113765168L;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限id列表
     */
    @NotEmpty(message = "角色关联权限不能为空")
    private List<String> permissionIdList;

}
