package com.hsbc.incident.infrastructure.generator;

import com.hsbc.incident.shared.utils.SpringUtils;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

@Slf4j
public class IdGenerator implements IdentifierGenerator {

    private final SnowflakeIdGenerator snowflakeIdGenerator
        = SpringUtils.getBean(SnowflakeIdGenerator.class);

    public static final String NAME = "com.hsbc.incident.infrastructure.generator.IdGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return snowflakeIdGenerator.generateId();
    }

}

