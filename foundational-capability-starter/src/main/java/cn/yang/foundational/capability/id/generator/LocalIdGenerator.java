package cn.yang.foundational.capability.id.generator;

import com.github.f4b6a3.ulid.UlidCreator;

import java.util.UUID;

/**
 * id生成器实现
 */
public class LocalIdGenerator implements IdGenerator {

    public LocalIdGenerator() {
    }

    @Override
    public String getId() {
        return UlidCreator.getUlid().toString();
    }

    @Override
    public String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
