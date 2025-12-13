package cn.yang.foundational.capability.username;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 用户名生成工具类
 *
 * @author : 未见清海
 */
@Slf4j
public class UsernameGeneratorImpl implements UsernameGenerator {

    private static final Random random = new Random();

    /**
     * 获取用户名
     *
     * @param length 用户名长度
     * @return 用户名
     */
    @Override
    public String getUsername(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 获取昵称
     *
     * @param length 昵称长度
     * @return 昵称
     */
    @Override
    public String getNickname(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String str = null;
            int heightPose, lowPos; // 定义高低位

            heightPose = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = Integer.valueOf(heightPose).byteValue();
            b[1] = Integer.valueOf(lowPos).byteValue();
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                log.error("generator username error", ex);
            }
            ret.append(str);
        }
        return ret.toString();
    }
}
