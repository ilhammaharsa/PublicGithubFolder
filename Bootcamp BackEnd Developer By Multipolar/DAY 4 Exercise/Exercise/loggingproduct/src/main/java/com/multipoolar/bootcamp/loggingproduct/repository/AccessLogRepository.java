package com.multipoolar.bootcamp.loggingproduct.repository;

import com.multipoolar.bootcamp.loggingproduct.domain.AccessLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessLogRepository extends MongoRepository<AccessLog, String> {
}
