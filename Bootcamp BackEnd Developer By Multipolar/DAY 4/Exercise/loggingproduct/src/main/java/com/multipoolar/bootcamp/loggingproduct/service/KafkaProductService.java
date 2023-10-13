package com.multipoolar.bootcamp.loggingproduct.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipoolar.bootcamp.loggingproduct.domain.AccessLog;
import com.multipoolar.bootcamp.loggingproduct.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProductService {
    private final ObjectMapper objectMapper;
    private final AccessLogRepository accessLogRepository;
    @Autowired
    public KafkaProductService(ObjectMapper objectMapper, AccessLogRepository accessLogRepository) {
        this.objectMapper = objectMapper;
        this.accessLogRepository = accessLogRepository;
    }
    @KafkaListener(topics = "loggingproduct")
    public void recieveMessage(String message){
        try{
            AccessLog accessLog = objectMapper.readValue(message,AccessLog.class);
            System.out.println("Recieve message : "+accessLog);
            accessLogRepository.insert(accessLog);
        }catch(Exception e){
            System.err.println("Error processing message: "+e.getMessage());
        }
    }
}
