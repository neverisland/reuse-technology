package cn.yang.authentication.authorization.user.enums;

import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.common.data.structure.enums.EnabledEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 初始系统用户
 *
 * @author : QingHai
 */
@Data
public class InitialUser implements Serializable {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String username = "yangyi";

    /**
     * 昵称
     */
    private String nickname = "杨依";

    /**
     * 手机号
     */
    private String phone = "15715822200";

    /**
     * 邮箱
     */
    private String email = "";

    /**
     * 是否未锁定 false 锁定 true 解锁
     * <br>
     * 参见 {@link AccountNonLockedEnum}
     */
    private Boolean accountNonLocked = true;

    /**
     * 锁定时间
     */
    private LocalDateTime lockedTime;

    /**
     * 是否启用 false 禁用 true 启用
     * <br>
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled = true;
}
