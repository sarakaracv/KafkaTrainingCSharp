package org.example;

import java.util.Properties;

public class BasicProducer {

    public static void main(String[] args) {
        System.out.println("starting basic producer");

        Properties setting= new Properties(); //configuration
        setting.put("client.id","basic-producer-v0.11.0");
        setting.put("bootstrap.serves","kafka-1:9092,kafka-2:9092");
        setting.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        setting.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String,String> producer= new KafkaProducer<>(setting); //producer creating

        Runtime.getRuntime().addShutdownHook(new Thread(()->{ //shutdown behaviour
            System.out.println("stopping basic producer");
        }));

        final String topic="starting topic";
        for (int i=1; i<=5; i++){ // sending data
            final String key="key"+i;
            final String value= "value"+i;
            final ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
            producer.send(record);
        }

    }
}
