package cn.yang.authentication.authorization.user.dto.user;

import cn.yang.common.data.structure.vo.page.PageQuery;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户分页查询入参
 *
 * @author : 未见清海
 */
@Data
public class UserPageQuery extends PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -3821527877786543387L;

    /**
     * 搜索关键字
     */
    private String searchText;

    /**
     * 用户状态，是否启用
     */
    private Boolean enabled;

}
