package cn.yang.authentication.authorization.user.controller;

import cn.yang.authentication.authorization.user.dto.role.RoleDto;
import cn.yang.authentication.authorization.user.dto.user.*;
import cn.yang.authentication.authorization.user.entity.User;
import cn.yang.authentication.authorization.user.enums.RoleEnum;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.authentication.authorization.user.facade.RoleFacade;
import cn.yang.authentication.authorization.user.facade.UserFacade;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.exception.BusinessException;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.common.data.structure.vo.page.PageResult;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import cn.yang.authentication.authorization.enums.ErrorStatusCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户身份接口
 *
 * @author : 未见清海
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserFacade userFacade;

    @Resource
    private IdentityFacade identityFacade;

    @Resource
    private RoleFacade roleFacade;

    /**
     * 分页查询用户数据
     *
     * @param userPageQuery 分页查询入参
     * @return 用户列表数据
     */
    @PostMapping("/page")
    public ResultVo<PageResult<UserPageDto>> pageData(@RequestBody UserPageQuery userPageQuery) {
        if (userPageQuery == null || userPageQuery.getCurrent() == null || userPageQuery.getSize() == null) {
            throw new BusinessException(ErrorStatusCodeEnum.PARAMETER_VERIFICATION_EXCEPTION, "分页查询数据不能为空");
        }
        PageResult<UserPageDto> pageResult = userFacade.pageData(userPageQuery);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success", pageResult);
    }

    /**
     * 新增用户数据
     */
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public ResultVo<String> insertData(@Validated @RequestBody UserInsertDto userInsertDto) {
        // 新增用户
        String userId = userFacade.insertUser(userInsertDto);
        // 新增普通用户身份
        RoleDto roleDto = roleFacade.selectByMark(RoleEnum.ORDINARY_USERS.getMark());
        identityFacade.insertIdentity(userId, List.of(roleDto.getId()));
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "新增成功", userId);
    }

    /**
     * 修改用户数据
     *
     * @param userUpdateDto 修改用户数据
     * @return 是否成功响应
     */
    @PostMapping("/update")
    public ResultVo<?> updateDate(@Validated @RequestBody UserUpdateDto userUpdateDto) {
        userFacade.updateUser(userUpdateDto);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "修改成功", null);
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param id 用户id
     * @return 用户详情
     */
    @GetMapping("/selectById")
    public ResultVo<UserDto> selectById(@RequestParam("id") String id) {
        // 获取用户数据
        User user = userFacade.selectById(id);
        UserDto userDto = BeanConvertUtils.convert(user, UserDto.class);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success", userDto);
    }

    /**
     * 禁用/启用用户
     */
    @PostMapping("/disableEnableUser")
    public ResultVo<?> disableEnableUser(@Validated @RequestBody DisableEnableUserDto disableEnableUserDto) {
        userFacade.disableEnableUser(disableEnableUserDto);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success");
    }

    /**
     * 根据用户id删除用户
     *
     * @param id 用户id
     * @return 请求响应
     */
    @GetMapping("/deleteById")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public ResultVo<?> deleteById(@RequestParam("id") String id) {
        // 删除用户数据
        userFacade.deleteById(id);
        // 删除身份数据
        identityFacade.deleteByUserId(id);
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "success");
    }
}
