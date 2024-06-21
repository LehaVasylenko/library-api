package com.example.consumer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JsonUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String prettifyJson(String json) {
        try {
            Object jsonObject = objectMapper.readValue(json, Object.class);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(jsonObject);
        } catch (Exception e) {
            log.error(e.getMessage());
            return json;
        }
    }
}
