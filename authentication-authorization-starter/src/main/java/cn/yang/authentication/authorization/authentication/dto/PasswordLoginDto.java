package cn.yang.authentication.authorization.authentication.dto;

import cn.yang.authentication.authorization.sliding.verification.dto.SlideCaptchaCheckDto;
import jakarta.validation.constraints.NotBlank;

/**
 * 账号密码登录入参
 *
 * @author : 未见清海
 */
public class PasswordLoginDto extends SlideCaptchaCheckDto {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordLoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
