package com.github.fabriciolfj.sqsexamples.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperties {

    public String url;
    public String region;
}
