package cn.yang.foundational.capability.username;

/**
 * 随机生成用户名
 *
 * @author : 未见清海
 */
public interface UsernameGenerator {

    /**
     * 获取用户名
     *
     * @param length 用户名长度
     * @return 用户名
     */
    String getUsername(int length);

    /**
     * 获取昵称
     *
     * @param length 昵称长度
     * @return 昵称
     */
    String getNickname(int length);
}
