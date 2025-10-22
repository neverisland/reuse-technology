package cn.yang.authentication.authorization.user.service;

import cn.yang.authentication.authorization.user.entity.Identity;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.RoleFacade;
import cn.yang.authentication.authorization.user.repository.IdentityRepository;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.enums.EnabledEnum;
import cn.yang.common.data.structure.enums.ErrorStatusCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 身份 业务层
 *
 * @author 未见清海
 */
@Service
public class IdentityService implements IdentityFacade {

    @Resource
    private IdentityRepository identityRepository;

    @Resource
    private RoleFacade roleFacade;

    /**
     * 新增用户身份
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return 身份id
     */
    @Override
    public String insertIdentity(String userId, List<String> roleIds) {
        // 校验用户id和角色id列表是否为空
        if (userId == null || roleIds == null || roleIds.isEmpty()) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "新增身份时,用户id和角色数据不能为空");
        }

        // 新增身份
        Identity identity = new Identity();
        identity.setUserId(userId);
        identity.setEnabled(EnabledEnum.ENABLE.getEnabled());
        // 复制身份的角色数据
        List<Role> roleList = roleFacade.selectByIds(roleIds);
        if (roleList == null || roleList.isEmpty() || roleList.size() != roleIds.size()) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "新增身份时,角色数据异常");
        }
        identity.setRoleList(roleList);
        return identityRepository.insertData(identity);
    }

    /**
     * 分配身份角色数据
     *
     * @param identityId 身份id
     * @param roleIdList 角色id列表
     */
    @Override
    public void assigningRoles(String identityId, List<String> roleIdList) {
        // 校验角色信息是否存在
        List<Role> roleList = roleFacade.selectByIds(roleIdList);
        if (roleList == null || roleList.isEmpty() || roleList.size() != roleIdList.size()) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "分配角色时,角色数据异常");
        }

        // 分配角色
        identityRepository.assigningRoles(identityId, roleIdList);
    }

    /**
     * 根据用户id获取身份列表数据
     *
     * @param userId@return 身份列表数据
     */
    @Override
    public List<Identity> selectByUserId(String userId) {
        try {
            return identityRepository.selectByUserId(userId);
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_DOES_NOT_EXIST, e.getMessage());
        }
    }

    /**
     * 根据身份id获取用户id
     *
     * @param identityId 身份id
     * @return 用户id
     */
    @Override
    public String selectUserIdByIdentityId(String identityId) {
        Identity identity = identityRepository.selectById(identityId);
        return identity.getUserId();
    }

    /**
     * 根据当前身份id获取角色列表
     *
     * @param identityId 身份id
     * @return 角色列表
     */
    @Override
    public List<Role> getRoleListByIdentityId(String identityId) {
        try {
            Identity identity = identityRepository.selectDetailById(identityId);
            return identity.getRoleList();
        } catch (NullDataException e) {
            throw new BusinessException(ErrorStatusCodeEnum.DATA_DOES_NOT_EXIST, e.getMessage());
        }
    }

    /**
     * 根据用户id删除身份数据
     *
     * @param userId 用户id
     */
    @Override
    public void deleteByUserId(String userId) {
        if (userId == null) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "用户id不能为空");
        }
        identityRepository.deleteByUserId(userId);
    }
}




