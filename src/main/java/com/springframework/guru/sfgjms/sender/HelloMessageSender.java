/* 
User: Urmi
Date: 3/5/2020 
Time: 3:24 PM
*/

package com.springframework.guru.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.guru.sfgjms.config.JmsConfig;
import com.springframework.guru.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("hello world")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, (session) -> {
            Message hello = null;
            try {
                hello = session.createTextMessage(objectMapper.writeValueAsString(message));
                hello.setStringProperty("_type", "com.springframework.guru.sfgjms.model.HelloWorldMessage");
                System.out.println("Sending hello");
                return hello;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new JMSException("error");
            }
        });
        System.out.println(receivedMsg.getBody(String.class));

    }
}
