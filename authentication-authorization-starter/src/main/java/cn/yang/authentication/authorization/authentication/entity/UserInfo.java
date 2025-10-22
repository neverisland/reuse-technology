package cn.yang.authentication.authorization.authentication.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author : 未见清海
 */
@Data
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1156308445860066924L;
    
    /**
     * 当前登录用户id
     */
    private String userId;

    /**
     * 当前登录身份id
     */
    private String identityId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

}
