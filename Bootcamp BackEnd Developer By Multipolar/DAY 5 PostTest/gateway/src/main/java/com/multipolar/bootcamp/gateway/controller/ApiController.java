package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.AccountDTO;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String ACCOUNT_URL = "http://localhost:8081/account";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private AccessLogService logService;

    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }
    @GetMapping
    public ResponseEntity<?> getAccounts(HttpServletRequest request) throws JsonProcessingException {
        String CLIENT_IP =  request.getRemoteAddr();
        String USER_AGENT = request.getHeader("User-Agent");
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(ACCOUNT_URL, new ParameterizedTypeReference<Object>(){});
            AccessLog accessLog = new AccessLog("GET",ACCOUNT_URL,response.getStatusCodeValue(),response.getBody().toString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET",ACCOUNT_URL,ex.getRawStatusCode(),ex.getResponseBodyAsString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        String CLIENT_IP =  request.getRemoteAddr();
        String USER_AGENT = request.getHeader("User-Agent");
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(ACCOUNT_URL+"/id/"+id, new ParameterizedTypeReference<>(){});
            AccessLog accessLog = new AccessLog("GET_ID",ACCOUNT_URL,response.getStatusCodeValue(),response.getBody().toString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET_ID",ACCOUNT_URL,ex.getRawStatusCode(),ex.getResponseBodyAsString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
    @PostMapping
    public ResponseEntity<?> postAccount(@RequestBody AccountDTO accountDTO, HttpServletRequest request) throws JsonProcessingException {
        String CLIENT_IP =  request.getRemoteAddr();
        String USER_AGENT = request.getHeader("User-Agent");
        try{
            ResponseEntity<?> response = restTemplateUtil.post(ACCOUNT_URL,accountDTO,AccountDTO.class);
            AccessLog accessLog = new AccessLog("POST",ACCOUNT_URL,response.getStatusCodeValue(),response.getBody().toString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("POST",ACCOUNT_URL,ex.getRawStatusCode(),ex.getResponseBodyAsString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, HttpServletRequest request,@RequestBody AccountDTO accountDTO) throws JsonProcessingException {
        String CLIENT_IP =  request.getRemoteAddr();
        String USER_AGENT = request.getHeader("User-Agent");
        try{
            ResponseEntity<?> response = restTemplateUtil.put(ACCOUNT_URL+"/id/"+id,accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog("PUT",ACCOUNT_URL,response.getStatusCodeValue(),response.getBody().toString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("PUT",ACCOUNT_URL,ex.getRawStatusCode(),ex.getResponseBodyAsString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        String CLIENT_IP =  request.getRemoteAddr();
        String USER_AGENT = request.getHeader("User-Agent");
        try{
            ResponseEntity<?> response = restTemplateUtil.delete(ACCOUNT_URL+"/id/"+id);
            AccessLog accessLog = new AccessLog("DEL",ACCOUNT_URL,response.getStatusCodeValue(),"DELETE SUCCESS",CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.noContent().build();
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("PUT",ACCOUNT_URL,ex.getRawStatusCode(),ex.getResponseBodyAsString(),CLIENT_IP,USER_AGENT,LocalDateTime.now());
            logService.logAccess((accessLog));
            return ResponseEntity.noContent().build();
        }
    }
}
