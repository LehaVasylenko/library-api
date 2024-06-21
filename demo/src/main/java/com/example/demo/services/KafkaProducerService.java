package com.example.demo.services;

import com.example.demo.dto.EmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaProducerService {

    static final String TOPIC_TELEGRAM = "controller-events";
    static final String TOPIC_EMAIL = "email";

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Async
    public void sendMessageTelegram(Map<String, Object> message) {
        try {
            kafkaTemplate.send(TOPIC_TELEGRAM, message);
        } catch (KafkaException ex) {
            log.error("Kafka producer fails: {}", ex.getMessage());
        }
    }

    @Async
    public void sendEmail(EmailDTO emailDTO) {
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(emailDTO);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        kafkaTemplate.send(TOPIC_EMAIL, jsonMessage);
    }
}
