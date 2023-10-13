package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.ProductDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String PRODUCT_URL="http://localhost:8081/product";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private AccessLogService logService;
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }
    @GetMapping
    public ResponseEntity<?> getProducts() throws JsonProcessingException{
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<Object>(){});
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"getProduct Success");
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now(),"getProduct Failed");
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }
    }
    @GetMapping("/getProductsId/{id}")
    public ResponseEntity<?> getProducts(@PathVariable String id) throws JsonProcessingException{
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL+"/id/"+id, new ParameterizedTypeReference<>(){});
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(),LocalDateTime.now(),"getProduct Success");
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now(),"getProduct Failed");
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }

    }
    @PostMapping
    public ResponseEntity<?>postProduct(@RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("CREATE",PRODUCT_URL,response.getStatusCodeValue(),LocalDateTime.now(),"createProduct Success");
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("CREATE",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now(),"createProduct failed");
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try{
            ResponseEntity<?> response = restTemplateUtil.put(PRODUCT_URL+"/id/"+id, productDTO,ProductDTO.class);
            AccessLog accessLog = new AccessLog("UPDATE",PRODUCT_URL,response.getStatusCodeValue(),LocalDateTime.now(),"updateProduct Success");
            logService.logAccess((accessLog));
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("UPDATE",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now(),"updateProduct failed");
            logService.logAccess((accessLog));
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) throws JsonProcessingException {
        try{
            ResponseEntity<?> response = restTemplateUtil.delete(PRODUCT_URL+"/id/"+id);
            AccessLog accessLog = new AccessLog("DELETE",PRODUCT_URL,response.getStatusCodeValue(),LocalDateTime.now(),"deleteProduct Success");
            logService.logAccess((accessLog));
            return ResponseEntity.noContent().build();
        }catch(HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("UPDATE",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now(),"updateProduct failed");
            logService.logAccess((accessLog));
            return ResponseEntity.noContent().build();
        }

    }

}
