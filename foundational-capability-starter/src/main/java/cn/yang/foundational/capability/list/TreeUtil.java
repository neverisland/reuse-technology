package cn.yang.foundational.capability.list;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 */
public class TreeUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 列表转树形结构
     *
     * @param list       原始列表
     * @param idField    id字段名
     * @param pidField   pid字段名
     * @param childField 子节点字段名
     * @param clazz      输出类型
     */
    public static <T> List<T> listToTree(List<?> list,
                                         String idField,
                                         String pidField,
                                         String childField,
                                         Class<T> clazz) {

        // 将对象列表转换成 Map 列表
        List<Map<String, Object>> dataList = mapper.convertValue(
                list, new TypeReference<List<Map<String, Object>>>() {
                }
        );

        List<Map<String, Object>> result = new ArrayList<>();

        // 用于快速查找父节点
        Map<Object, Map<String, Object>> hash = new LinkedHashMap<>();

        // 先缓存所有节点
        for (Map<String, Object> item : dataList) {
            hash.put(item.get(idField), item);
        }

        // 构建树
        for (Map<String, Object> item : dataList) {

            Object pidVal = item.get(pidField);
            Map<String, Object> parent = hash.get(pidVal);

            if (parent != null) {
                // 取 children
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> children = (List<Map<String, Object>>) parent.get(childField);

                if (children == null) {
                    children = new ArrayList<>();
                    parent.put(childField, children);
                }

                children.add(item);
            } else {
                // 根节点
                result.add(item);
            }
        }

        // 转换回泛型类型
        return mapper.convertValue(
                result,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }
}