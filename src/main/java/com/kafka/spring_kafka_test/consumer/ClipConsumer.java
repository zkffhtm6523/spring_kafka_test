package com.kafka.spring_kafka_test.consumer;

import com.kafka.spring_kafka_test.model.Animal;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;

@Service
public class ClipConsumer {

    // Chapter4
    //                                                              concurrency : Thread 갯수, clientIdPrefix : clientId에 prefix 붙이기
//    @KafkaListener(id = "clip4-listener", topics = "clip4-listener", concurrency = "2", clientIdPrefix = "listener-id")
//    public void listen(String message,
//                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
//                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
//                       @Header(KafkaHeaders.OFFSET) long offset,
//                       ConsumerRecordMetadata metadata){
//        // metadata : header 정보 가져오기
//        System.out.println("Listener. offset=" + metadata.offset() +
//                ", timestamp=" + new Date(timestamp) +
//                ", partition=" + partition +
//                ", message=" + message
//        );
//    }
    // 포맷이 변경되었기 때문에 topic은 별도로 다시 지정해주자
//    @KafkaListener(id = "clip4-animal-listener", topics = "clip4-animal", containerFactory = "kafkaJsonContainerFactory")
    //                      @valid 체크로 나이 15 체크해서 에러 발생
    @KafkaListener(id = "clip3-animal-listener", topics = "clip3-animal", containerFactory = "kafkaJsonContainerFactory")
    public void listenAnimal(@Valid Animal animal){
        System.out.println("Animal. animal="+animal);
    }

    @KafkaListener(id = "clip3-animal.DLT-listener", topics = "clip3-animal.DLT", containerFactory = "kafkaJsonContainerFactory")
    public void listenAnimalDLT(Animal animal){
        System.out.println("DLT Animal. animal="+animal);
    }

    // chapter3
//    @KafkaListener(id = "clip3-id", topics = "clip3")
//    public void listenClip3(String message){
//        System.out.println(message);
//    }
//
//    @KafkaListener(id = "clip3-bytes-id", topics = "clip3-bytes")
//    public void listenClip3Bytes(String message){
//        System.out.println(message);
//    }
//
//    @KafkaListener(id = "clip3-request-id", topics = "clip3-request")
//    @SendTo
//    public String listenClip3Request(String message){
//        System.out.println(message);
//        return "Pong Clip3";
//    }

}
