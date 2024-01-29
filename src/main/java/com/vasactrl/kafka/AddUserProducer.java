package com.vasactrl.kafka;

import com.mangut.basedomain.dtos.EmailEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AddUserProducer {
    public static final Logger LOGGER = LoggerFactory.getLogger(AddUserProducer.class);

    private NewTopic newTopic;
    private KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public AddUserProducer(NewTopic newTopic, KafkaTemplate<String, EmailEvent> kafkaTemplate) {
        this.newTopic = newTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailEvent event) {
        LOGGER.info(String.format("Email event => %s", event.toString()));

        Message<EmailEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, newTopic.name())
                .build();
        kafkaTemplate.send(message);
    }

}


