package com.kafka.spring_kafka_test;

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

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class SpringKafkaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaTestApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate) {
//        return args -> {
//            kafkaTemplate.send("quickstart-events", "hello-world");
//        };
//    }
    @Bean
    public ApplicationRunner runner(AdminClient adminClient){
        return args -> {
            Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
            for (String topicName : topics.keySet()){
                TopicListing topicListing = topics.get(topicName);
                System.out.println(topicListing); // 토픽 이름 출력

                Map<String, TopicDescription> description = adminClient.describeTopics(Collections.singleton(topicName)).all().get();
                System.out.println(description); // 토픽 정보 출력

                if(!topicListing.isInternal()){
                    adminClient.deleteTopics(Collections.singleton(topicName));
                }
            }
        };
    }
}
