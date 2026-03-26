package com.aws.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.credentials.region}")
    private String region;

    @Bean("dynamoDbClient")
    @Profile("local")
    DynamoDbClient getLocalDynamoDBClient(
            @Value("${aws.credentials.accessKey}") String accessKey,
            @Value("${aws.credentials.secretKey}") String secretKey) {
        System.out.println("local bean: "+accessKey+" || "+secretKey);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        DynamoDbClient client = DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
        return client;
    }

    @Bean("dynamoDbClient")
    @Profile("live")
    DynamoDbClient getLiveDynamoDBClient() {
        return DynamoDbClient.builder().region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create()).build();
    }

    @Bean
    DynamoDbEnhancedClient getEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }

}
