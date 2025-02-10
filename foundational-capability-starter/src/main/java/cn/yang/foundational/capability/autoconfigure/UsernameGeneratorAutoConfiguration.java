package cn.yang.foundational.capability.autoconfigure;

import cn.yang.foundational.capability.username.UsernameGenerator;
import cn.yang.foundational.capability.username.UsernameGeneratorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户名生成器自动装配
 *
 * @author : 未见清海
 */
@Configuration
public class UsernameGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UsernameGenerator usernameGenerator() {
        return new UsernameGeneratorImpl();
    }
}
