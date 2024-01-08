package com.itau.teste.transport.common.sns.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.itau.teste.core.common.util.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class SNSConfiguration {
    private final AWSCredentialsProvider credentials;
    private final EndpointConfiguration endpoint;

    public SNSConfiguration(
        @Value("app.aws.access-key") String accessKey,
        @Value("app.aws.secret-key") String secretKey,
        @Value("app.aws.endpoint") String endpoint,
        @Value("app.aws.region") String region
    ) {
        credentials = new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(accessKey, secretKey)
        );
        this.endpoint = new EndpointConfiguration(endpoint, region);
    }

    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClient.builder()
            .withCredentials(credentials)
            .withEndpointConfiguration(endpoint)
            .build();
    }

    @Bean
    public NotificationMessagingTemplate template(AmazonSNS sns) {
        return new NotificationMessagingTemplate(sns);
    }
}
