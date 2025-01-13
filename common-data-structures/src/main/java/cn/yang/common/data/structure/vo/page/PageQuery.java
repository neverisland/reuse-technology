package cn.yang.common.data.structure.vo.page;

/**
 * 分页查询条件
 *
 * @author : 未见清海
 */
public class PageQuery {

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
    private Integer offset;

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

    public Integer getOffset() {
        return (current - 1) * size;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "current=" + current +
                ", size=" + size +
                '}';
    }
}
