package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.IdentityDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface IdentityMapper {

    /**
     * 新增数据
     *
     * @param identityDo 新增数据
     * @return 新增条数
     */
    int insert(IdentityDo identityDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param identityDo 新增数据
     * @return 新增条数
     */
    int insertSelective(IdentityDo identityDo);

    /**
     * 批量新增数据
     *
     * @param identityDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<IdentityDo> identityDoList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    IdentityDo selectDetailById(String id);

    /**
     * 根据主键修改数据
     *
     * @param identityDo 修改数据
     * @return 修改条数
     */
    int updateById(IdentityDo identityDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param identityDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(IdentityDo identityDo);

    /**
     * 根据用户id删除身份数据
     *
     * @param userId 数据id
     * @return 删除数据
     */
    int deleteByUserId(String userId);

    /**
     * 根据用户id获取身份列表数据
     *
     * @param userId 用户id
     * @return 身份列表数据
     */
    List<IdentityDo> selectByUserId(@Param("userId") String userId);

    /**
     * 根据身份id获取身份详情
     *
     * @param identityId 身份id
     * @return 身份详情
     */
    IdentityDo selectById(String identityId);
}




