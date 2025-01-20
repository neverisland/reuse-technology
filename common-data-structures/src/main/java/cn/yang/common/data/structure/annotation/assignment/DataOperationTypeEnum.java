package cn.yang.common.data.structure.annotation.assignment;

/**
 * 数据操作类型
 * @author : 未见清海
 */
public enum DataOperationTypeEnum {

    CREATE("create", "新增数据操作"),

    UPDATE("update", "更新数据操作"),

    ;

    /**
     * 操作类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;

    DataOperationTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
