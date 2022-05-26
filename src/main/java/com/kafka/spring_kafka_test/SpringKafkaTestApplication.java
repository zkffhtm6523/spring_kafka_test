package com.kafka.spring_kafka_test;

import com.kafka.spring_kafka_test.producer.ClipProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class SpringKafkaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaTestApplication.class, args);
    }

    // chapter3
    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate){ // 기존에 있던 것
    public ApplicationRunner runner(ClipProducer clipProducer){ // 직접 만든 것[비동기]
        return args -> {
            clipProducer.async("clip3", "Hello, clip3-async");
            clipProducer.sync("clip3", "Hello, Clip3-sync");
//            Thread.sleep(1000L);
            clipProducer.routingSend("clip3", "Hello, Clip3-routing");
            clipProducer.routingSendByts("clip3-bytes", "Hello, Clip3-bytes".getBytes(StandardCharsets.UTF_8));
        };
    }

//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate) {
//        return args -> {
//            kafkaTemplate.send("quickstart-events", "hello-world");
//        };
//    }
    // chapter2
//    @Bean
//    public ApplicationRunner runner(AdminClient adminClient){
//        return args -> {
//            Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
//            for (String topicName : topics.keySet()){
//                TopicListing topicListing = topics.get(topicName);
//                System.out.println(topicListing); // 토픽 이름 출력
//
//                Map<String, TopicDescription> description = adminClient.describeTopics(Collections.singleton(topicName)).all().get();
//                System.out.println(description); // 토픽 정보 출력
//
//                if(!topicListing.isInternal()){
//                    adminClient.deleteTopics(Collections.singleton(topicName));
//                }
//            }
//        };
//    }

}
