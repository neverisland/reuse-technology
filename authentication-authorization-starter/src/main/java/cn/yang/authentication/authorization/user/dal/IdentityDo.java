package cn.yang.authentication.authorization.user.dal;

import cn.yang.authentication.authorization.user.enums.IdentityTypeEnum;
import cn.yang.common.data.structure.entity.BaseEntity;
import cn.yang.common.data.structure.enums.EnabledEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 身份
 *
 * @author 未见清海
 */
@Data
public class IdentityDo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 9021445789293088491L;

    /**
     * 身份id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 身份类型
     * <br>
     * 参见 {@link IdentityTypeEnum}
     */
    private Integer type;

    /**
     * 是否启用 0 禁用 1 启用
     * <br>
     * 参见 {@link EnabledEnum}
     */
    private Boolean enabled;

    /**
     * 身份管理角色列表
     */
    private List<RoleDo> roleList;
}
