package cn.yang.authentication.authorization.user.dto.user;

import cn.yang.common.data.structure.enums.AccountNonLockedEnum;
import cn.yang.common.data.structure.enums.EnabledEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户详情出参
 *
 * @author : 未见清海
 */
@Data
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8688559601566746917L;

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
     * 参见 {@link AccountNonLockedEnum}
     */
    private Boolean accountNonLocked;

    /**
     * 是否启用 0 禁用 1 启用
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

}
