package cn.yang.authentication.authorization.authentication.mapper;

import cn.yang.authentication.authorization.authentication.dal.AuthenticationHistoricalPasswordDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface AuthenticationHistoricalPasswordMapper {

    /**
     * 新增数据
     *
     * @param AuthenticationHistoricalPasswordDo 新增数据
     * @return 新增条数
     */
    int insert(AuthenticationHistoricalPasswordDo AuthenticationHistoricalPasswordDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param AuthenticationHistoricalPasswordDo 新增数据
     * @return 新增条数
     */
    int insertSelective(AuthenticationHistoricalPasswordDo AuthenticationHistoricalPasswordDo);

    /**
     * 批量新增数据
     *
     * @param AuthenticationHistoricalPasswordDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<AuthenticationHistoricalPasswordDo> AuthenticationHistoricalPasswordDoList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    AuthenticationHistoricalPasswordDo selectById(String id);

    /**
     * 根据主键修改数据
     *
     * @param AuthenticationHistoricalPasswordDo 修改数据
     * @return 修改条数
     */
    int updateById(AuthenticationHistoricalPasswordDo AuthenticationHistoricalPasswordDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param AuthenticationHistoricalPasswordDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(AuthenticationHistoricalPasswordDo AuthenticationHistoricalPasswordDo);

    /**
     * 根据id删除数据
     *
     * @param id 数据id
     * @return 删除数据
     */
    int deleteById(Long id);

}




