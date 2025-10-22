package cn.yang.authentication.authorization.user.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改
 *
 * @author : 未见清海
 */
@Data
public class UserUpdateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4148294729450530234L;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     * 没有的话自动赋值
     */
    private String nickname;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 邮箱
     */
    private String email;

}
