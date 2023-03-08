package com.e2mg.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 描述
 *
 * @author EdiwalMusk
 * @date 2023/2/26 8:17
 */
public class CustomConsumer {

    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.94.194:9092,192.168.94.195:9092");

        // 反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 消费组ID
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        // 自动提交offset
        // properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 定义主题
        List<String> topics = new ArrayList<>();
        topics.add("test302");
        consumer.subscribe(topics);

        // 消费数据
        while (true) {
            ConsumerRecords<String, String> consumeRecords = consumer.poll(Duration.ofSeconds(1));
            consumeRecords.forEach(System.out::println);
            consumer.commitSync();
        }
    }
}
