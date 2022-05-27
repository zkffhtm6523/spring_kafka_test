package com.kafka.spring_kafka_test.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class ClipConsumer {

    @KafkaListener(id = "clip3-id", topics = "clip3")
    public void listenClip3(String message){
        System.out.println(message);
    }

    @KafkaListener(id = "clip3-bytes-id", topics = "clip3-bytes")
    public void listenClip3Bytes(String message){
        System.out.println(message);
    }

    @KafkaListener(id = "clip3-request-id", topics = "clip3-request")
    @SendTo
    public String listenClip3Request(String message){
        System.out.println(message);
        return "Pong Clip3";
    }

}
