package com.hsbc.incident.config;

import com.hsbc.incident.infrastructure.generator.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultBeanConfig {

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        // For simplicity, we are hardcoding the workerId here.
        // In a real-world scenario, we should get the workerId from Redis or a database.
        // We can use Redisson to get the workerId from Redis.
        int workerId = 1;
        int dataCenterId = 0;
        return new SnowflakeIdGenerator(workerId, dataCenterId);
    }
}
