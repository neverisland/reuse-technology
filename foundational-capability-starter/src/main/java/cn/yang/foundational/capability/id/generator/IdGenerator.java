package cn.yang.foundational.capability.id.generator;

/**
 * ID生成接口
 *
 * @author : 未见清海
 */
public interface IdGenerator {

    /**
     * 获取String类型的ID
     *
     * @return ID
     */
    String getId();

    /**
     * 获取32位UUID
     *
     * @return ID
     */
    String get32UUID();
}
