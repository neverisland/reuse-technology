package cn.yang.authentication.authorization.user.entity;

import cn.yang.common.data.structure.entity.BaseEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表
 *
 * @author 未见清海
 */
@Data
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7079981934131594028L;

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
     */
    private Integer type;

    /**
     * 权限列表
     */
    private List<Permission> permissionList;

}
