package cn.yang.authentication.authorization.authentication.dto;

import cn.yang.authentication.authorization.authentication.entity.UserInfo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 当前登录用户信息
 *
 * @author : 未见清海
 */
@Data
public class CurrentUserInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1936736207031068482L;

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 当前登录身份角色列表
     */
    private List<CurrentUserRoleDto> roles;

    /**
     * 权限标识列表
     */
    private List<String> permissions;

    public CurrentUserInfoDto() {
    }

    public CurrentUserInfoDto(UserInfo userInfo, List<CurrentUserRoleDto> roles, List<String> permissions) {
        this.userInfo = userInfo;
        this.roles = roles;
        this.permissions = permissions;
    }
}
