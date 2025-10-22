package cn.yang.authentication.authorization.authentication.mapper;

import cn.yang.authentication.authorization.authentication.dal.AuthenticationPasswordDo;
import jakarta.validation.constraints.NotEmpty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据接口层
 *
 * @author 未见清海
 */
@Mapper
public interface AuthenticationPasswordMapper {

    /**
     * 新增数据
     *
     * @param AuthenticationPasswordDo 新增数据
     * @return 新增条数
     */
    int insert(AuthenticationPasswordDo AuthenticationPasswordDo);

    /**
     * 批量新增数据
     *
     * @param AuthenticationPasswordDoList 批量新增数据
     * @return 新增条数
     */
    int insertBatch(@Param("list") List<AuthenticationPasswordDo> AuthenticationPasswordDoList);

    /**
     * 根据id修改数据(过滤为空数据)
     *
     * @param AuthenticationPasswordDo 修改数据
     * @return 修改条数
     */
    int updateById(AuthenticationPasswordDo AuthenticationPasswordDo);

    /**
     * 根据用户id获取密码认证数据
     *
     * @param userId 用户id
     * @return 密码认证数据
     */
    AuthenticationPasswordDo selectByUserId(@NotEmpty(message = "密码认证,用户id不能为空") @Param("userId") String userId);
}