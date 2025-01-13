package cn.yang.common.data.structure.vo.page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装
 *
 * @author : 未见清海
 */
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

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResultVo{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
