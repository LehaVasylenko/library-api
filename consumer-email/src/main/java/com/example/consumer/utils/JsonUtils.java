package com.example.consumer.utils;

import com.example.consumer.model.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    public static EmailDTO getEmailDTO(String message) {
        try {
            return objectMapper.readValue(message, EmailDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
