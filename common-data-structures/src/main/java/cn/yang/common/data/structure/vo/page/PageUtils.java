package cn.yang.common.data.structure.vo.page;

import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.exception.BusinessException;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 分页工具类
 *
 * @author : QingHai
 */
@Slf4j
@SuppressWarnings("unused")
public class PageUtils {

    private PageUtils() {

    }

    /**
     * 执行分页查询
     *
     * @param pageQuery 分页查询入参
     * @param callable  查询方法
     * @param <T>       返回数据类型
     * @return 分页结果pageQuery
     */
    public static <T extends Serializable> PageResult<T> doSelectPage(PageQuery pageQuery, Callable<List<T>> callable) {
        // 设置分页参数
        try (Page<T> objects = PageMethod.startPage(pageQuery.getCurrent(), pageQuery.getSize())) {
            // 业务查询
            List<T> list = callable.call();
            return toPageResultRO(list);
        } catch (Exception ex) {
            throw new BusinessException(StatusCodeEnum.UN_KNOW, "查询失败", ex);
        } finally {
            PageMethod.clearPage();
        }
    }

    /**
     * 将Page转换为PageResult
     *
     * @param list 分页查询结果
     * @param <T>  数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> toPageResultRO(List<T> list) {
        Page<T> page = (Page<T>) list;
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        int total = (int) page.getTotal();
        // 创建PageResult对象并设置属性
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrent(pageNum);
        pageResult.setSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setList(page);
        return pageResult;
    }

}