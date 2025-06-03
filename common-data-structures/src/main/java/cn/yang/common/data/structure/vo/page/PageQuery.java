package cn.yang.common.data.structure.vo.page;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author : 未见清海
 */
@Data
public class PageQuery implements Serializable {

    /**
     * 当前页 ,分页使用
     */
    private Integer current;

    /**
     * 每页条数 ,分页使用
     */
    private Integer size;

    /**
     * 偏移量
     */
    @Setter(AccessLevel.NONE)
    private Integer offset;

    public Integer getOffset() {
        return (current - 1) * size;
    }

}
