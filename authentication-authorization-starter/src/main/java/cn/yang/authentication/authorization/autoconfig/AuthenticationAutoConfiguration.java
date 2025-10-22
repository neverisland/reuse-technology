package cn.yang.authentication.authorization.autoconfig;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : QingHai
 */
@Configuration
@ComponentScan("cn.yang.authentication.authorization")
@ConditionalOnProperty(prefix = "authentication.authorization", value = "enabled")
@MapperScan({"cn.yang.authentication.authorization.user.mapper", "cn.yang.authentication.authorization.authentication.mapper"})
public class AuthenticationAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAutoConfiguration.class);

    public AuthenticationAutoConfiguration() {
        logger.info("已加载认证授权服务...");
    }
}
