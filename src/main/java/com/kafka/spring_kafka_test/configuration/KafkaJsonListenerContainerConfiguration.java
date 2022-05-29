package com.kafka.spring_kafka_test.configuration;

import com.kafka.spring_kafka_test.model.Animal;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class KafkaJsonListenerContainerConfiguration implements KafkaListenerConfigurer {

    private final LocalValidatorFactoryBean validator;

    public KafkaJsonListenerContainerConfiguration(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Animal>> kafkaJsonContainerFactory(
            KafkaTemplate<String, Animal> kafkaJsonTemplate
    ){
        ConcurrentKafkaListenerContainerFactory<String, Animal> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(animalConsumerFactory());
        // 에러 핸들러 예제 2
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaJsonTemplate)));

//      에러 핸들러 예제 1
//        // error handler
//        factory.setRetryTemplate(customizedRetryTemplate());
//
//        // 에러 발생 시 recovery 작동 해서 정상적인 메세지 나오도록
//        factory.setRecoveryCallback(context -> {
//            ConsumerRecord record = (ConsumerRecord) context.getAttribute("record");
//            System.out.println("Recovery callback, message=" + record.value());
//            throw new RuntimeException("RuntimeException");
//        });
//        // 에러 핸들러 작동
//        factory.setErrorHandler(((thrownException, data) -> System.out.println("Error handler, exception" + thrownException.getMessage())));
        return factory;
    }

    private RetryTemplate customizedRetryTemplate() {
        return new RetryTemplateBuilder()
                .fixedBackoff(1_000) //
                .customPolicy(retryPolicy())
                .build();
    }

    private RetryPolicy retryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
        exceptions.put(ListenerExecutionFailedException.class, true); // true 시 에러 발생하면 재시도

        return new SimpleRetryPolicy(3, exceptions); // 반복 횟수 3회
    }

    private ConsumerFactory<String, Animal> animalConsumerFactory() {
        // json 역직렬화 시 에러 발생
        return new DefaultKafkaConsumerFactory<>(props(), new StringDeserializer(), new JsonDeserializer<>(Animal.class));
    }

    private Map<String, Object> props() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }
}
