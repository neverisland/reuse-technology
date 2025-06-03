package cn.yang.common.data.structure.vo.page;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果封装
 *
 * @author : 未见清海
 */
@Data
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7022619929991383955L;

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 数据集合
     */
    private List<T> list;

    public PageResult() {
    }

    public PageResult(PageQuery pageQuery) {
        this.current = pageQuery.getCurrent();
        this.size = pageQuery.getSize();
        this.total = 0;
        this.list = new ArrayList<>();
    }

    public PageResult(PageQuery pageQuery, List<T> list, Integer total) {
        this.current = pageQuery.getCurrent();
        this.size = pageQuery.getSize();
        this.total = total;
        this.list = list;
    }

}
