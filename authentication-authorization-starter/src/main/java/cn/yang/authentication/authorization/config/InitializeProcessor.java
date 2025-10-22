package cn.yang.authentication.authorization.config;

import cn.yang.authentication.authorization.authentication.entity.AuthenticationPassword;
import cn.yang.authentication.authorization.authentication.enums.PasswordTypeEnums;
import cn.yang.authentication.authorization.authentication.repository.AuthenticationPasswordRepository;
import cn.yang.authentication.authorization.autoconfig.AuthenticationProperties;
import cn.yang.authentication.authorization.user.dto.role.RoleDto;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.authentication.authorization.user.enums.InitialUser;
import cn.yang.authentication.authorization.user.enums.RoleEnum;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.PermissionFacade;
import cn.yang.authentication.authorization.user.facade.RoleFacade;
import cn.yang.authentication.authorization.user.repository.UserRepository;
import cn.yang.common.data.structure.exception.NullDataException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 初始化处理器
 *
 * @author : 未见清海
 */
@Configuration
public class InitializeProcessor {

    private final static Logger logger = LoggerFactory.getLogger(InitializeProcessor.class);

    @Resource
    private PermissionFacade permissionFacade;

    @Resource
    private RoleFacade roleFacade;

    @Resource
    private UserRepository userRepository;

    @Resource
    private IdentityFacade identityFacade;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationPasswordRepository authenticationPasswordRepository;

    @Resource
    private AuthenticationProperties authenticationProperties;

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        logger.info("认证服务数据初始化...");

        // 初始化角色权限
        permissionFacade.init();
        roleFacade.init();
        initUserData();

        logger.info("认证服务数据初始化完成...");
    }

    /**
     * 初始化系统管理员用户
     */
    private void initUserData() {
        InitialUser initialUser = new InitialUser();
        try {
            userRepository.selectByUsername(initialUser.getUsername());
        } catch (NullDataException e) {
            // 新增系统用户
            User user = BeanConvertUtils.convert(initialUser, User.class);
            String userId = userRepository.insertData(user);
            // 新增系统管理员身份
            RoleDto roleDto = roleFacade.selectByMark(RoleEnum.SYSTEM_ADMINISTRATOR.getMark());
            identityFacade.insertIdentity(userId, List.of(roleDto.getId()));
            // 新增密码认证方式
            AuthenticationPassword authenticationPassword = new AuthenticationPassword();
            authenticationPassword.setUserId(userId);
            authenticationPassword.setPassword(passwordEncoder.encode(authenticationProperties.getSystemUserInitPassword()));
            authenticationPassword.setPasswordType(PasswordTypeEnums.INITIAL_PASSWORD.getType());
            authenticationPasswordRepository.insertData(authenticationPassword);
        }
    }
}
