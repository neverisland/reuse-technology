package cn.yang.authentication.authorization.config;

import cn.yang.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : 未见清海
 */
@Configuration
public class AuthBeanConfig {

    @Bean
    @Lazy
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

}
