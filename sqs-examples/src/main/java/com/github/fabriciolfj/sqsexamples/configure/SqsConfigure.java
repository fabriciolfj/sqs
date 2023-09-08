package com.github.fabriciolfj.sqsexamples.configure;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SqsConfigure {

    @Bean
    @Primary
    public AmazonSQSAsync amazonSqsAsync(SqsProperties properties) {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(properties.url, properties.region)
                )
                .build();
    }

    @Bean
    public QueueMessageHandler queueMessageHandler(AmazonSQSAsync amazonSQSAsync) {
        var handler = new QueueMessageHandlerFactory();
        handler.setAmazonSqs(amazonSQSAsync);
        return handler.createQueueMessageHandler();
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(AmazonSQSAsync amazonSQSAsync, QueueMessageHandler handler) {
        var simple = new SimpleMessageListenerContainer();
        simple.setAmazonSqs(amazonSQSAsync);
        simple.setMessageHandler(handler);
        simple.setMaxNumberOfMessages(10);
        simple.setWaitTimeOut(20);

        return simple;
    }
}
