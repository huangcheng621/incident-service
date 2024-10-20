package com.hsbc.incident.config;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
    value = "com.hsbc.incident.domain.repository",
    repositoryBaseClass = BaseJpaRepositoryImpl.class
)
@Configuration
public class JpaConfig {

}
