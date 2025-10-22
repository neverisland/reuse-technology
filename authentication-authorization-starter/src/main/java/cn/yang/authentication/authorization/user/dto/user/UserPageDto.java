package cn.yang.authentication.authorization.user.dto.user;

import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.common.data.structure.enums.EnabledEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户分页展示数据
 *
 * @author : 未见清海
 */
@Data
public class UserPageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 884802111178144505L;

    /**
     * 用户id
     */
    private String id;

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
     * 是否未锁定 false 锁定 true 解锁
     * <br>
     * 参见 {@link AccountNonLockedEnum}
     */
    private Boolean accountNonLocked;

    /**
     * 是否启用 false 禁用 true 启用
     * <br>
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled;

}
