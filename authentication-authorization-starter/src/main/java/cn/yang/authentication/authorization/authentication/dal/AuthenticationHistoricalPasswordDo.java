package cn.yang.authentication.authorization.authentication.dal;

import cn.yang.common.data.structure.entity.BaseTimeEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户历史密码
 *
 * @author 未见清海
 */
@Data
public class AuthenticationHistoricalPasswordDo extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 852309722001405983L;

    /**
     * Id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 密码的哈希值
     */
    private String passwordHash;

}
