package com.wissda.LearningSession.MongoConfig;

import com.azure.identity.ClientSecretCredential;
import com.azure.security.keyvault.secrets.SecretClient;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitializingMongoConfig {
    private MongoDBProperties properties;

    private ClientSecretCredential clientSecretCredential;
    private SecretClient secretClient;
    private List<String> hosts;

    @Value("${spring.data.mongodb.port}")
    String portNumber;
    @Value("${spring.data.mongodb.username}")
    String usernameKey;
    @Value("${spring.data.mongodb.password}")
    String passwordKey;
    @Value("${spring.data.mongodb.authentication-database}")
    String authDbName;
    @Value("${azure.keyvault.secrets.mongodb.replica-set}")
    String replicaSetKey;
    @Value("${spring.data.mongodb.database}")
    String databaseKey;
    @Value("${blob.storage-account}")
    String accountNameKey;
    @Value("${blob.connection-string}")
    String connectionStringKey;
    @Value("${blob.sas-token}")
    String sasTokenKey;

    @Autowired
    public InitializingMongoConfig(MongoDBProperties properties) {
        this.properties = properties;
    }

    //String databaseKey="csm-db";
    String databaseName;
    //String portNumber="db-port";
    Integer port;
    //String usernameKey="qa-db-username";
    String username;
    //String passwordKey="qa-db-password";
    String password;
    //String authDbName="auth-db";
    String authDatabase;
    String replicaSet;
    String accountName;
    String connectionString;
    String sasToken;

    @PostConstruct
    public void init() {
        this.clientSecretCredential = properties.getClientSecretCredential();
        this.secretClient = properties.getSecretClient();

        String host1 = secretClient.getSecret(properties.getHostName1()).getValue();
        String host2 = secretClient.getSecret(properties.getHostName2()).getValue();
        String host3 = secretClient.getSecret(properties.getHostName3()).getValue();
        this.hosts = Arrays.asList(host1,host2,host3);

        databaseName = secretClient.getSecret(databaseKey).getValue();
        port= Integer.parseInt(portNumber);
        username=secretClient.getSecret(usernameKey).getValue();
        password=secretClient.getSecret(passwordKey).getValue();
        authDatabase=secretClient.getSecret(authDbName).getValue();
        replicaSet=secretClient.getSecret(replicaSetKey).getValue();

        accountName=secretClient.getSecret(accountNameKey).getValue();
        connectionString=secretClient.getSecret(connectionStringKey).getValue();
        sasToken=secretClient.getSecret(sasTokenKey).getValue();
    }
}
