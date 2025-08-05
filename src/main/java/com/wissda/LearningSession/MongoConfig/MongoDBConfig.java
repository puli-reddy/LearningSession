package com.wissda.LearningSession.MongoConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Slf4j
@EnableMongoRepositories(basePackages = "com.wissda")
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private MongoDBConnectionString connectionString;

    @Autowired
    private MongoDBProperties mongoDBProperties;

    @Autowired
    private InitializingMongoConfig initializingMongoConfig;

    @Override
    protected String getDatabaseName() {
        return initializingMongoConfig.getDatabaseName();
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public com.mongodb.client.MongoClient mongoClient() {
        return com.mongodb.client.MongoClients.create(connectionString.getConnectionString());
    }
}
