package cn.yang.authentication.authorization.user.dal;

import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.common.data.structure.entity.BaseEntity;
import cn.yang.common.data.structure.enums.EnabledEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author 未见清海
 */
@Data
public class UserDo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4738320771180540610L;

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
     * 邮箱
     */
    private String email;

    /**
     * 是否未锁定 false 锁定 true 解锁
     * <br>
     * 参见 {@link AccountNonLockedEnum}
     */
    private Boolean accountNonLocked;

    /**
     * 锁定时间
     */
    private LocalDateTime lockedTime;

    /**
     * 是否启用 false 禁用 true 启用
     * <br>
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled;

}
