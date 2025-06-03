package cn.yang.common.data.structure.annotation.assignment;

import cn.yang.common.data.structure.exception.NotLoginException;

/**
 * 获取当前登录用户接口
 *
 * @author : 未见清海
 */
@FunctionalInterface
public interface CurrentlyUserFacade {

    /**
     * 获取当前登录用户id
     *
     * @return 当前登录用户id
     */
    String getCurrentlyUserId() throws NotLoginException;

}
