package com.multipolar.bootcamp.logging.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.logging.domain.AccessLog;
import com.multipolar.bootcamp.logging.repository.AccessLogRepository;
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
    @KafkaListener(topics = "access-logs-accounts")
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
