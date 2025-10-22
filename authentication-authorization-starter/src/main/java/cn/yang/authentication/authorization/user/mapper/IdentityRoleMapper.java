package cn.yang.authentication.authorization.user.mapper;

import cn.yang.authentication.authorization.user.dal.IdentityRoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface IdentityRoleMapper {

    /**
     * 新增数据
     *
     * @param identityRoleDo 新增数据
     * @return 新增条数
     */
    int insert(IdentityRoleDo identityRoleDo);

    /**
     * 新增数据(过滤为空数据)
     *
     * @param identityRoleDo 新增数据
     * @return 新增条数
     */
    int insertSelective(IdentityRoleDo identityRoleDo);

    /**
     * 批量新增数据
     *
     * @param identityRoleDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<IdentityRoleDo> identityRoleDoList);

    /**
     * 根据id查询数据
     *
     * @param id 数据id
     * @return 数据
     */
    IdentityRoleDo selectById(Long id);

    /**
     * 根据主键修改数据
     *
     * @param identityRoleDo 修改数据
     * @return 修改条数
     */
    int updateById(IdentityRoleDo identityRoleDo);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param identityRoleDo 修改数据
     * @return 修改条数
     */
    int updateSelectiveById(IdentityRoleDo identityRoleDo);

    /**
     * 根据身份id删除数据
     *
     * @param identityId 身份id
     * @return 删除数据
     */
    int deleteByIdentityId(String identityId);

}




