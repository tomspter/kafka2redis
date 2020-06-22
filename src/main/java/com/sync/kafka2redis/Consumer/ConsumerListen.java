package com.sync.kafka2redis.Consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sync.kafka2redis.pojo.KafkaMessage;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ConsumerListen {

    private Gson gson=new GsonBuilder().create();

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(ConsumerRecord<String,String> record,String topic){
        Optional<String> messageValue = Optional.ofNullable(record.value());
        if (messageValue.isPresent()) {
            String realValue = messageValue.get().trim();
            log.info("get message topic:{},key:{},value:{}",topic,record.key(), realValue);
            //TODO 创建实体类，反序列化
            KafkaMessage kafkaMessage = gson.fromJson(realValue, KafkaMessage.class);
        }
    }
}
