package cn.yang.authentication.authorization.user.controller;

import cn.yang.authentication.authorization.user.dto.identity.AssigningRoleDto;
import cn.yang.authentication.authorization.user.facade.IdentityFacade;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 身份接口
 *
 * @author : 未见清海
 */
@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Resource
    private IdentityFacade identityFacade;

    /**
     * 获取用户的身份列表
     *
     * @param userId 用户id
     * @return 身份列表
     */
    @GetMapping("/selectListByUserId")
    public ResultVo<List<?>> selectDataByUserId(@RequestParam("userId") Long userId) {

        return ResultFactory.success(StatusCodeEnum.SUCCESS, List.of());
    }


    /**
     * 为身份分配角色
     *
     * @param assigningRoleDto 分配角色入参
     * @return 是否成功响应
     */
    @PostMapping("/assigningRoles")
    public ResultVo<?> assigningRoles(@Validated @RequestBody AssigningRoleDto assigningRoleDto) {
        identityFacade.assigningRoles(assigningRoleDto.getIdentityId(), assigningRoleDto.getRoleIdList());
        return ResultFactory.success(StatusCodeEnum.SUCCESS, "分配成功");
    }
}
