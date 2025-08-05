package com.wissda.LearningSession.MongoConfig;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MongoDBProperties {

    // Replace with your Azure Key Vault URL
    @Value("${spring.keyvault.url}")
    String keyVaultUrl ;
    // Replace with your Azure Active Directory Tenant ID
    @Value("${spring.keyvault.tenantId}")
    String tenantId;
    // Replace with your Azure Active Directory App ID (Client ID)
    @Value("${spring.keyvault.clientId}")
    String clientId ;
    // Replace with your Azure Active Directory Client Secret
    @Value("${spring.keyvault.clientSecret}")
    String clientSecret;

    @Value("${spring.data.mongodb.database}")
    String databaseKey;
    @Value("${spring.data.mongodb.host}")
    private String mongoHosts;

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

    // Authenticate with Azure Key Vault using Azure Identity credentials
    ClientSecretCredential clientSecretCredential ;

    // Create a SecretClient to interact with Azure Key Vault
    SecretClient secretClient ;

    String[] hostArray ;//= mongoHosts.split(",");
    String hostName1;// = hostArray[0];
    String hostName2;// = hostArray[1];
    String hostName3 ;//= hostArray[2];

    @PostConstruct
    public void init() {
        hostArray = mongoHosts.split(",");

        hostName1 = hostArray[0];
        hostName2 = hostArray[1];
        hostName3 = hostArray[2];

        clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(clientSecretCredential)
                .buildClient();
    }

}
