package cn.yang.authentication.authorization.authentication.repository;

import cn.yang.authentication.authorization.authentication.mapper.AuthenticationHistoricalPasswordMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;


/**
 * 用户历史密码 仓储层
 *
 * @author 未见清海
 */
@Repository
public class AuthenticationHistoricalPasswordRepository {

    @Resource
    private AuthenticationHistoricalPasswordMapper AuthenticationHistoricalPasswordMapper;

    public void insertData() {

    }

}




