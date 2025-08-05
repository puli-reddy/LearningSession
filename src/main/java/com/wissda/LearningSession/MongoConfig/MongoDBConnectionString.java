package com.wissda.LearningSession.MongoConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MongoDBConnectionString {
    private final MongoDBProperties properties;
    private final InitializingMongoConfig initializingMongoConfig;

    @Autowired
    public MongoDBConnectionString(MongoDBProperties properties,InitializingMongoConfig initializingMongoConfig) {
        this.properties = properties;
        this.initializingMongoConfig=initializingMongoConfig;
    }

    public String getConnectionString() {

        String hostAndPort="";
        int size = initializingMongoConfig.getHosts().size();
        for(int i=0;i<size;i++) {
            hostAndPort =hostAndPort+ initializingMongoConfig.getHosts().get(i)+":"+initializingMongoConfig.getPort()+",";
        }
        log.info("inside mongo db connection string class");
        log.info("hostAnd port "+hostAndPort);
        hostAndPort=hostAndPort.substring(0,hostAndPort.length()-1);
        String connectionString = "mongodb://" +
                initializingMongoConfig.getUsername() + ":" +
                initializingMongoConfig.getPassword() + "@" + hostAndPort+
                "/?replicaSet="
                +initializingMongoConfig.getReplicaSet()+"&authSource="+
                initializingMongoConfig.getAuthDatabase();

        return connectionString;
    }
}
