package cn.yang.authentication.authorization.user.repository;

import cn.yang.authentication.authorization.user.dal.IdentityDo;
import cn.yang.authentication.authorization.user.dal.IdentityRoleDo;
import cn.yang.authentication.authorization.user.entity.Identity;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.authentication.authorization.user.mapper.IdentityMapper;
import cn.yang.authentication.authorization.user.mapper.IdentityRoleMapper;
import cn.yang.common.data.structure.annotation.assignment.BaseDataAssignment;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.foundational.capability.id.generator.IdGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 身份 仓储层
 *
 * @author 未见清海
 */
@Component
public class IdentityRepository {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private IdentityMapper identityMapper;

    @Resource
    private IdentityRoleMapper identityRoleMapper;

    /**
     * 新增身份数据
     *
     * @param identity 身份数据
     * @return 身份id
     */
    @BaseDataAssignment
    @Transactional(rollbackFor = Exception.class)
    public String insertData(Identity identity) {
        // 新增身份
        IdentityDo identityDo = BeanConvertUtils.convert(identity, IdentityDo.class);
        identityDo.setId(idGenerator.getId());
        identityMapper.insert(identityDo);

        // 分配身份角色
        List<String> roleIdList = identity.getRoleList().stream().map(Role::getId).toList();
        assigningRoles(identityDo.getId(), roleIdList);
        return identityDo.getId();
    }

    /**
     * 身份分配角色
     *
     * @param identityId 身份id
     * @param roleIdList 角色id列表
     */
    public void assigningRoles(String identityId, List<String> roleIdList) {
        // 删除身份关联角色
        identityRoleMapper.deleteByIdentityId(identityId);
        // 新增身份角色关联数据
        List<IdentityRoleDo> identityRoleDoList = new ArrayList<>();
        roleIdList.forEach(roleId -> {
            IdentityRoleDo identityRoleDo = new IdentityRoleDo();
            identityRoleDo.setId(idGenerator.getId());
            identityRoleDo.setIdentityId(identityId);
            identityRoleDo.setRoleId(roleId);
            identityRoleDoList.add(identityRoleDo);
        });
        identityRoleMapper.insertBatch(identityRoleDoList);
    }

    /**
     * 根据用户id获取身份列表数据
     *
     * @param userId 用户id
     * @return 身份列表数据
     */
    public List<Identity> selectByUserId(String userId) throws NullDataException {
        List<IdentityDo> identityDoList = identityMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(identityDoList)) {
            throw new NullDataException("身份数据不存在");
        }
        return BeanConvertUtils.convert(identityDoList, Identity.class);
    }

    /**
     * 根据身份id获取身份数据
     *
     * @param identityId 身份id
     * @return 身份数据
     */
    public Identity selectById(String identityId) {
        IdentityDo identityDo = identityMapper.selectById(identityId);
        return BeanConvertUtils.convert(identityDo, Identity.class);
    }


    /**
     * 根据身份id获取详细信息(包含关联角色信息等)
     *
     * @param identityId 身份id
     * @return 身份详细信息
     */
    public Identity selectDetailById(String identityId) throws NullDataException {
        IdentityDo identityDo = identityMapper.selectDetailById(identityId);
        if (identityDo == null) {
            throw new NullDataException("身份数据不存在");
        }
        Identity identity = BeanConvertUtils.convert(identityDo, Identity.class);

        if (!CollectionUtils.isEmpty(identity.getRoleList())) {
            identity.setRoleList(BeanConvertUtils.convert(identity.getRoleList(), Role.class));
        }
        return identity;
    }

    /**
     * 根据用户id删除身份列表数据
     *
     * @param userId 用户id
     */
    public void deleteByUserId(String userId) {
        identityMapper.deleteByUserId(userId);
    }
}




