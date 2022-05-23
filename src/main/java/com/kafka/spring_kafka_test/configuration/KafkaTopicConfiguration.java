package com.kafka.spring_kafka_test.configuration;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {


    // chapter3
    @Bean
    public KafkaAdmin.NewTopics clip3s(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("clip3").build()
        );
    }



    // chapter2-1
    // 신규 토픽 생성
//    @Bean
//    public NewTopic clip2(){
//        return TopicBuilder.name("clip2").build();
//    }

    // chapter2-2
    // 신규 토픽 여러개 생성
//    @Bean
//    public KafkaAdmin.NewTopics clip2s(){
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("clip2-part1").build(),
//                TopicBuilder.name("clip2-part2")
//                        .partitions(3)
//                        .replicas(1)
//                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 6))
//                        .build()
//        );
//    }
    // chapter1
    // 카프카에서 정보 받아오는 역할, runner에서 사용할 수 있다.
//    @Bean
//    public AdminClient adminClient(KafkaAdmin kafkaAdmin){
//        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
//    }
}
