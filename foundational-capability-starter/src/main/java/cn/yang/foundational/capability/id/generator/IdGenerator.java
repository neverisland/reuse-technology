package cn.yang.foundational.capability.id.generator;

/**
 * ID生成接口
 *
 * @author : 未见清海
 */
public interface IdGenerator {

    /**
     * 获取Long类型的ID
     *
     * @return ID
     */
    Long getLongId();

    /**
     * 获取32位UUID
     *
     * @return ID
     */
    String get32UUID();
}
