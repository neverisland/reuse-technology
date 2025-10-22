package cn.yang.authentication.authorization.user.enums;

import cn.yang.common.data.structure.exception.NullDataException;
import lombok.Getter;

/**
 * 权限枚举
 *
 * @author : 未见清海
 */
@Getter
public enum PermissionEnum {

    BASIC_PERMISSIONS("basic_permissions", "基础权限"),

    MANAGEMENT_ROLE("management_role", "管理角色权限"),

    ;

    /**
     * 权限标识
     */
    private final String mark;

    /**
     * 描述
     */
    private final String description;

    PermissionEnum(String mark, String description) {
        this.mark = mark;
        this.description = description;
    }

    /**
     * 根据权限标识获取权限数据
     *
     * @param mark 权限标识
     * @return 如果不存在空异常
     */
    public static PermissionEnum getPermissionEnumByMark(String mark) throws NullDataException {
        for (PermissionEnum permissionEnum : values()) {
            if (permissionEnum.mark.equals(mark)) {
                return permissionEnum;
            }
        }
        throw new NullDataException("根据权限标识获取权限数据异常");
    }
}
