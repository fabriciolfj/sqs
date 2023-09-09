package com.github.fabriciolfj.sqsexamples.listener;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExampleQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleQueueListener.class);

    @SqsListener(value = "receive.fifo")
    public void process(final String payload) {
        LOGGER.info("receive message {}", payload);
    }
}
