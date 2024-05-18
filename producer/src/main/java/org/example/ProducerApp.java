package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Hello world!
 */


public class ProducerApp {
    public static void main(String[] args) throws InterruptedException {
        final Properties properties = new Properties();
        final int NUMBER_OF_RECORD = 1000;
        final long PAUSE_MILLIS = 1000L;
        final String topicName = "iot-data";


        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < NUMBER_OF_RECORD; i++) {
                String key = "sensor-" + i;
                String value = "Temperature: " + (Math.random() * 100);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, key, value);
                System.out.println("Produced message: (" + key + ", " + value + ")");
                kafkaProducer.send(producerRecord, ProducerApp::callBack);
                Thread.sleep(PAUSE_MILLIS);
            }
        }
    }

    private static void callBack(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            System.out.println("Error occurs : " + e.getMessage());
        } else {
            System.out.println("recordMetadata : " + recordMetadata.toString());
        }
    }
}