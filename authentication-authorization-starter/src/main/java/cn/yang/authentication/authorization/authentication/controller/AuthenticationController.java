package cn.yang.authentication.authorization.authentication.controller;

import cn.yang.authentication.authorization.authentication.dto.CurrentUserInfoDto;
import cn.yang.authentication.authorization.authentication.dto.CurrentUserRoleDto;
import cn.yang.authentication.authorization.authentication.entity.UserInfo;
import cn.yang.authentication.authorization.authentication.facade.AuthenticationFacade;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.entity.Role;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.RoleFacade;
import cn.yang.authentication.authorization.user.facade.UserFacade;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权
 *
 * @author : 未见清海
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Resource
    private AuthenticationFacade authenticationFacade;

    @Resource
    private IdentityFacade identityFacade;

    @Resource
    private UserFacade userFacade;

    @Resource
    private RoleFacade roleFacade;

    /**
     * 获取认证信息
     *
     * @param userId 用户id
     * @return 认证方式
     */
    @GetMapping("/selectAuthenticationMethod")
    public ResultVo<List<Integer>> selectAuthenticationMethod(@RequestParam("userId") String userId) {
        List<Integer> authenticationMethods = authenticationFacade.selectAuthenticationMethod(userId);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success", authenticationMethods);
    }

    /**
     * 校验当前用户是否已经认证
     *
     * @return 是否认证
     */
    @GetMapping("/verifyWhetherAuthenticated")
    public ResultVo<Boolean> verifyWhetherAuthenticated() {
        Boolean flag = authenticationFacade.verifyWhetherAuthenticated();
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success", flag);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return 当前登录用户信息
     */
    @GetMapping("/getCurrentUserInfo")
    public ResultVo<CurrentUserInfoDto> getCurrentUserInfo() {
        UserInfo userInfo = authenticationFacade.getCurrentUserInfo();
        // 获取当前用户角色信息
        List<Role> currentUserRoleList = identityFacade.getRoleListByIdentityId(userInfo.getIdentityId());
        List<CurrentUserRoleDto> currentUserRoleDtoList = new ArrayList<>();
        List<String> permissionMarkList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(currentUserRoleList)) {
            currentUserRoleDtoList = BeanConvertUtils.convert(currentUserRoleList, CurrentUserRoleDto.class);
            // 获取当前用户的权限信息
            List<String> roleIdList = currentUserRoleList.stream().map(Role::getId).toList();
            List<Permission> permissionList = roleFacade.selectPermissionListByRoleIds(roleIdList);
            permissionMarkList = permissionList.stream().map(Permission::getMark).toList();
        }
        CurrentUserInfoDto currentUserInfoDto = new CurrentUserInfoDto(userInfo, currentUserRoleDtoList, permissionMarkList);

        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success", currentUserInfoDto);
    }

}
