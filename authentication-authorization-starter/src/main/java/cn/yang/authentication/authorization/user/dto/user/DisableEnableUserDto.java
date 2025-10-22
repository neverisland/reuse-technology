package cn.yang.authentication.authorization.user.dto.user;

import cn.yang.common.data.structure.enums.EnabledEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 禁用/启用用户数据
 * @author : 未见清海
 */
@Data
public class DisableEnableUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2774882945788097414L;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private String userId;

    /**
     * 禁用状态
     * <br>
     * 参考{@link EnabledEnum}
     */
    @NotNull(message = "用户禁用状态不能为空")
    private Boolean enabled;

}
