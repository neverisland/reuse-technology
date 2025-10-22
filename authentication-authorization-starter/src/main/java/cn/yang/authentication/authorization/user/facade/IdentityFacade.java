package cn.yang.authentication.authorization.user.facade;

import cn.yang.authentication.authorization.user.entity.Identity;
import cn.yang.authentication.authorization.user.entity.Role;

import java.util.List;

/**
 * 身份 业务接口定义
 *
 * @author 未见清海
 */
public interface IdentityFacade {

    /**
     * 新增用户身份
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return 身份id
     */
    String insertIdentity(String userId, List<String> roleIds);

    /**
     * 分配身份角色数据
     *
     * @param identityId 身份id
     * @param roleIdList 角色id列表
     */
    void assigningRoles(String identityId, List<String> roleIdList);

    /**
     * 根据用户id获取身份列表数据
     *
     * @param userId 用户id
     * @return 身份列表数据
     */
    List<Identity> selectByUserId(String userId);

    /**
     * 根据身份id获取用户id
     *
     * @param identityId 身份id
     * @return 用户id
     */
    String selectUserIdByIdentityId(String identityId);

    /**
     * 根据当前身份id获取角色id列表
     *
     * @param identityId 身份id
     * @return 角色id列表
     */
    List<Role> getRoleListByIdentityId(String identityId);

    /**
     * 根据用户id删除身份数据
     *
     * @param userId 用户id
     */
    void deleteByUserId(String userId);
}
