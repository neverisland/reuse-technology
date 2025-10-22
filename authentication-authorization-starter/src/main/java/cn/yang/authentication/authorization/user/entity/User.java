package cn.yang.authentication.authorization.user.entity;

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
 * @author : 未见清海
 */
@Data
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2167693448651698874L;

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
     * 是否未锁定 0 锁定 1 解锁
     * <br>
     * 参见 {@link AccountNonLockedEnum}
     */
    private Boolean accountNonLocked;

    /**
     * 锁定时间
     */
    private LocalDateTime lockedTime;

    /**
     * 是否启用 0 禁用 1 启用
     * <br>
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled;

}
