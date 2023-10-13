package com.multipolar.bootcamp.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AccessLogService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logAccess(AccessLog accessLog) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String accessLogMessage = objectMapper.writeValueAsString(accessLog);
            kafkaTemplate.send("access-logs-products", accessLogMessage); //Bisa diganti access-log sesuai kebutuhan
        } catch (KafkaException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

