package cn.yang.authentication.authorization.user.dal;

import cn.yang.authentication.authorization.user.enums.RoleTypeEnum;
import cn.yang.common.data.structure.entity.BaseEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色持久数据结构
 *
 * @author : 未见清海
 */
@Data
public class RoleDo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4852587942356739585L;

    /**
     * 角色id
     */
    private String id;

    /**
     * 角色标识
     */
    private String mark;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色类型 0 内置角色 1 自定义角色
     * <br>
     * 参见 {@link RoleTypeEnum}
     */
    private Integer type;

    /**
     * 权限列表
     */
    private List<PermissionDo> permissionList;
}
