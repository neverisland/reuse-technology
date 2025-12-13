package cn.yang.foundational.capability.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * jackson的json处理工具类
 */
@SuppressWarnings("unused")
public class JsonUtils {

    private JsonUtils() {

    }

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 格式化时间格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        MAPPER.registerModule(new JavaTimeModule());
        //转义异常解决
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }

    /**
     * 把对象转换为json字符串
     *
     * @param data 数据
     * @return json字符串
     */
    public static String objectToString(Object data) throws JsonProcessingException {
        if (data == null) {
            return null;
        }
        return MAPPER.writeValueAsString(data);
    }

    /**
     * 字符串转为对象
     *
     * @param jsonString json字符串
     * @param beanType   转换类
     * @param <T>        泛型
     * @return 转换对象
     */
    public static <T> T stringToObject(String jsonString, Class<T> beanType) throws JsonProcessingException {
        return MAPPER.readValue(jsonString, beanType);
    }

    /**
     * 字符串转为对象指定类型
     *
     * @param json       json字符串
     * @param typeRef    类型
     * @param <T>        泛型
     * @return 转换对象
     */
    public static <T> T stringToObject(String json, TypeReference<T> typeRef) throws JsonProcessingException {
        return MAPPER.readValue(json, typeRef);
    }

    /**
     * 字符串转为对象指定类型
     *
     * @param jsonString json字符串
     * @param javaType   类型
     * @param <T>        泛型
     * @return 转换对象
     */
    public static <T> T stringToObject(String jsonString, JavaType javaType) throws JsonProcessingException {
        return MAPPER.readValue(jsonString, javaType);
    }

    /**
     * 字符串转换为数组对象
     *
     * @param jsonString json字符串
     * @param beanType   转换类
     * @param <T>        泛型
     * @return 转换数组对象
     */
    public static <T> List<T> stringToList(String jsonString, Class<T> beanType) throws JsonProcessingException {
        if (Objects.isNull(jsonString) || jsonString.isEmpty()) {
            return Collections.emptyList();
        }
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        return MAPPER.readValue(jsonString, javaType);
    }


    /**
     * json转JsonNode
     *
     * @param jsonStr json字符串
     * @return JsonNode
     */
    public static JsonNode jsonToJsonNode(String jsonStr) throws JsonProcessingException {
        return MAPPER.readTree(jsonStr);
    }
}