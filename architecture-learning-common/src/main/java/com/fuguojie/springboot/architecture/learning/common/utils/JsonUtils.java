package com.fuguojie.springboot.architecture.learning.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Json工具类
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 11:47
 * @since JDK 1.8
 */
public class JsonUtils {
    private static ObjectMapper objectMapper;

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public JsonUtils(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static <T> T readValue(String input, Class<T> type) {
        try {
            return objectMapper.readValue(input, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize the json " + input + " to object", e);
        }
    }

    public static <T> T readValue(byte[] input, Class<T> type) {
        try {
            return objectMapper.readValue(input, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize the json " + new String(input, StandardCharsets.UTF_8)
                    + " to object", e);
        }
    }

    public static void writeValue(OutputStream out, Object value) {
        try {
            objectMapper.writeValue(out, value);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize the object " + value + " to JSON.", e);
        }
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize the object " + value + " to JSON.", e);
        }
    }

    public static byte[] writeValueAsBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize the object " + value + " to JSON.", e);
        }
    }
}
