package cn.yang.authentication.authorization.authentication.repository;

import cn.yang.authentication.authorization.authentication.dal.AuthenticationPasswordDo;
import cn.yang.authentication.authorization.authentication.entity.AuthenticationPassword;
import cn.yang.authentication.authorization.authentication.mapper.AuthenticationPasswordMapper;
import cn.yang.common.data.structure.annotation.assignment.BaseDataAssignment;
import cn.yang.common.data.structure.annotation.assignment.DataOperationTypeEnum;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.foundational.capability.id.generator.IdGenerator;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;


/**
 * 用户密码认证 仓储层
 *
 * @author 未见清海
 */
@Component
public class AuthenticationPasswordRepository {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private AuthenticationPasswordMapper authenticationPasswordMapper;

    /**
     * 根据用户id获取密码认证数据
     *
     * @param userId 用户id
     * @return 密码认证数据
     */
    public AuthenticationPassword selectByUserId(@NotEmpty(message = "密码认证,用户id不能为空") String userId) throws NullDataException {
        AuthenticationPasswordDo AuthenticationPasswordDo = authenticationPasswordMapper.selectByUserId(userId);
        if (AuthenticationPasswordDo == null) {
            throw new NullDataException("密码认证数据不存在");
        }
        return BeanConvertUtils.convert(AuthenticationPasswordDo, AuthenticationPassword.class);
    }

    /**
     * 新增用户密码认证方式
     *
     * @param authenticationPassword 密码认证方式
     */
    @BaseDataAssignment
    public void insertData(AuthenticationPassword authenticationPassword) {
        AuthenticationPasswordDo AuthenticationPasswordDo = BeanConvertUtils.convert(authenticationPassword, AuthenticationPasswordDo.class);
        AuthenticationPasswordDo.setId(idGenerator.getId());

        authenticationPasswordMapper.insert(AuthenticationPasswordDo);
    }

    /**
     * 修改用户密码认证方式
     *
     * @param authenticationPassword 用户密码认证方式
     */
    @BaseDataAssignment(DataOperationTypeEnum.UPDATE)
    public void updateData(AuthenticationPassword authenticationPassword) {
        AuthenticationPasswordDo updateDate = BeanConvertUtils.convert(authenticationPassword, AuthenticationPasswordDo.class);
        authenticationPasswordMapper.updateById(updateDate);
    }
}




