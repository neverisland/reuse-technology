package cn.yang.foundational.capability.id.generator;

import java.util.UUID;

/**
 * id生成器实现
 */
public class LocalIdGenerator implements IdGenerator {

    private static final Sequence sequence = new Sequence();

    public LocalIdGenerator() {
    }

    @Override
    public Long getLongId() {
        return sequence.nextId();
    }

    @Override
    public String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
