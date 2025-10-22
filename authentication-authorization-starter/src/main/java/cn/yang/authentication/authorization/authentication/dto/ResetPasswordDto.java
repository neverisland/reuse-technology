package cn.yang.authentication.authorization.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 密码重置入参
 *
 * @author : 未见清海
 */
@Data
public class ResetPasswordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 847361661590197098L;

    /**
     * 用户id
     */
    @NotNull(message = "重置密码用户id不能为空")
    private String userId;

    /**
     * 重置的密码
     */
    @NotBlank(message = "重置的密码不能为空")
    private String password;
}
