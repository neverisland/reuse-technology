package cn.yang.authentication.authorization.user.dto.role;

import cn.yang.common.data.structure.vo.page.PageQuery;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色分页查询入参
 *
 * @author : 未见清海
 */
@Data
public class RolePageQuery extends PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 6525054552111338561L;

    /**
     * 搜索文本
     */
    private String searchText;

}
