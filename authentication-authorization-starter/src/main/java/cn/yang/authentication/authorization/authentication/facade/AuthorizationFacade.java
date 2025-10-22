package cn.yang.authentication.authorization.authentication.facade;

/**
 * 授权业务接口
 *
 * @author : 未见清海
 */
public interface AuthorizationFacade {

    /**
     * 根据用户id获取可以授权的身份
     *
     * @param userId 用户id
     * @return 可以授权的身份id
     */
    String getAuthorizedIdentity(String userId);

    /**
     * 授权根据身份id
     *
     * @param identityId 身份id
     * @return 授权标识
     */
    String authorizationByIdentityId(String identityId);
}
