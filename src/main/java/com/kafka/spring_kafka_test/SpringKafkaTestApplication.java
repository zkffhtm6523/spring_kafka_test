package com.kafka.spring_kafka_test;

import com.kafka.spring_kafka_test.model.Animal;
import com.kafka.spring_kafka_test.producer.ClipProducer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class SpringKafkaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaTestApplication.class, args);
    }

    // chapter4
    @Bean
    public ApplicationRunner runner(ClipProducer clipProducer,
                                    KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer){
        return args -> {
//            clipProducer.async("clip4", "Hello, Clip4 Container");
//            kafkaMessageListenerContainer.start(); // kafkaMessageListenerContainer에서 setAutoStartup을 false로 해줘서 수동으로 start함
//
//            Thread.sleep(1_000L);
//
//            System.out.println("-- pause --");
//            kafkaMessageListenerContainer.pause();
//            Thread.sleep(1_000L);
//
//            clipProducer.async("clip4", "Hello, Secondly Clip4 Container");
//
//            System.out.println("-- resume --");
//            kafkaMessageListenerContainer.resume();
//            Thread.sleep(1_000L);
//
//            System.out.println("-- stop --");
//            kafkaMessageListenerContainer.stop();
//
//            // clipConsumer 생성 직후
//            clipProducer.async("clip4-listener", "Hello, Clip4 Listener");

            // Animal 객체 생성 이후 Json으로
//            clipProducer.asyncAnimal("clip4-animal", new Animal("puppy", 15));

            // error handler
            clipProducer.asyncAnimal("clip3-animal", new Animal("puppy", 15));
        };
    }
    // chapter3
//    public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate){ // 기존에 있던 것
//    public ApplicationRunner runner(ClipProducer clipProducer){ // 직접 만든 것[비동기]
//        return args -> {
//            clipProducer.async("clip3", "Hello, clip3-async");
//            clipProducer.sync("clip3", "Hello, Clip3-sync");
//            clipProducer.routingSend("clip3", "Hello, Clip3-routing");
//            clipProducer.routingSendBytes("clip3-bytes", "Hello, Clip3-bytes".getBytes(StandardCharsets.UTF_8));
//            clipProducer.replyingSend("clip3-request", "Ping Clip3");
//        };
//    }

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
