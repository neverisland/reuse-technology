package cn.yang.authentication.authorization.user.dto.role;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色分页查询列表返回
 *
 * @author : 未见清海
 */
@Data
public class RolePageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1544451817025910058L;

    /**
     * 角色id
     */
    private String id;

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

}
