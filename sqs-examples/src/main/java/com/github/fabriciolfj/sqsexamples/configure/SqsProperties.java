package com.github.fabriciolfj.sqsexamples.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperties {

    private String url;
    private String region;

    public String getUrl() {
        return url;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
