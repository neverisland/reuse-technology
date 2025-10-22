package cn.yang.authentication.authorization.user.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新增用户入参实体
 *
 * @author : 未见清海
 */
@Data
public class UserInsertDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6829106047423931243L;

    /**
     * 用户名
     * 没有的话会自动赋值
     */
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
