package com.sinfor.stater.net.processor;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinfor.stater.net.exception.JsonException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Json通用处理类
 *
 * @author 李新周
 */
public class JsonUtil {

    private static ObjectMapper MAPPER;
    private static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    static {

        MAPPER = new ObjectMapper();
        // 不区分大小写
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);


        MAPPER.setSerializationInclusion(Include.NON_NULL);


        MAPPER.setDateFormat(new SimpleDateFormat(DATE_FORMAT_DATETIME));
        // 允许有未知字段
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符)
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许非双引号属性名字
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号包住字段
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    }

    public static ObjectMapper getMAPPER() {
        return MAPPER;
    }

    /**
     * 将对象转换成json字符串。
     */
    public static String objectToJson(Object data) {
        if (data == null) {
            return null;
        }
        String string;
        try {
            string = MAPPER.writeValueAsString(data);
        } catch (Exception e) {
            throw new JsonException("json转换异常;" + e.getMessage());
        }
        return string;
    }

    public static byte[] objectToBytes(Object data) {
        try {
            return MAPPER.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new JsonException("json转换异常;" + e.getMessage());
        }
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        T t;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            throw new JsonException("json转换异常" + e);
        }
        return t;

    }

    public static <T> T jsonToPojo(byte[] jsonData, Class<T> beanType) {
        T t;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            throw new JsonException("json转换异常.;" + e.getMessage());
        }
        return t;

    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);

        List<T> list;
        try {
            list = MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new JsonException("json转换异常;" + e.getMessage());
        }
        return list;

    }

    public static <T> List<T> jsonToList(byte[] jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);

        List<T> list;
        try {
            list = MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new JsonException("json转换异常;" + e.getMessage());
        }
        return list;

    }
}
