package cn.yang.common.data.structure.vo.page;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author : QingHai
 */
@Data
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -3934282999621552365L;

    /**
     * 当前页 ,分页使用
     */
    @NotNull(message = "当前页不能为空")
    private Integer current;

    /**
     * 每页条数 ,分页使用
     */
    @NotNull(message = "每页条数不能为空")
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
