package cn.yang.authentication.authorization.user.controller;

import cn.yang.authentication.authorization.user.dto.permission.PermissionDto;
import cn.yang.authentication.authorization.user.facade.PermissionFacade;
import cn.yang.common.data.structure.enums.StatusCodeEnum;
import cn.yang.common.data.structure.vo.result.ResultFactory;
import cn.yang.common.data.structure.vo.result.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限接口
 *
 * @author : 未见清海
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionFacade permissionFacade;

    /**
     * 获取所有权限
     *
     * @return 所有的权限数据
     */
    @GetMapping("/selectAllData")
    public ResultVo<List<PermissionDto>> selectAllData() {
        List<PermissionDto> permissionDtoList = permissionFacade.selectAllData();
        return ResultFactory.success(StatusCodeEnum.SUCCESS, permissionDtoList);
    }
}
