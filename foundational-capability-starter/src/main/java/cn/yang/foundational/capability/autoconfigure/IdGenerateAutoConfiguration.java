package cn.yang.foundational.capability.autoconfigure;

import cn.yang.foundational.capability.id.generator.IdGenerator;
import cn.yang.foundational.capability.id.generator.LocalIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * id生成器自动装配
 *
 * @author : 未见清海
 */
@Configuration
public class IdGenerateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return new LocalIdGenerator();
    }

}
