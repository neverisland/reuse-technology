package cn.yang.common.data.structure.vo.page;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页查询结果封装
 *
 * @author : QingHai
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


    /**
     * 构建一个空的分页对象
     */
    public static <T> PageResult<T> empty(PageQuery pageQuery) {
        PageResult<T> result = new PageResult<>();
        result.setCurrent(pageQuery.getCurrent());
        result.setSize(pageQuery.getSize());
        result.setTotal(0);
        result.setList(Collections.emptyList());
        return result;
    }

    /**
     * 替换数据
     *
     * @param newList 新增的列表
     * @param <R>     新的分页类型
     * @return 新的分页数据
     */
    public <R> PageResult<R> transLate(List<R> newList) {
        PageResult<R> result = new PageResult<>();
        result.setCurrent(this.current);
        result.setSize(this.size);
        result.setTotal(this.total);
        result.setList(newList == null ? Collections.emptyList() : newList);
        return result;
    }
}