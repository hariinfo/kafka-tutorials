package org.kafka.tutorials;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.streams.StreamsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProducer {

    public static void main(String args[]){
    final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

     // KafkaProducer<String, String> producer = new KafkaProducer<String, String>(new SimpleProducer().bootstrapProducer("localhost:9092"));

      // create the producer
      KafkaProducer<String, String> producer =
              new KafkaProducer<String, String>(new SimpleProducer().bootstrapProducer("localhost:9092"));
      
      // create a producer record
      ProducerRecord<String, String> record =
              new ProducerRecord<String, String>("events", "adjust", "-10");
     
      // Send in a for loop

      // send data - asynchronous
      producer.send(record, new Callback(){
          @Override
          public void onCompletion(RecordMetadata metadata, Exception exception) {
              if (exception == null){
                logger.info("Transmitted to Kafka: " +
                "Topic:" + metadata.topic() + "\n" +
                "Partition: " + metadata.partition() + "\n" +
                "Offset: " + metadata.offset() + "\n" +
                "Timestamp: " + metadata.timestamp());
              }
              else {
                logger.error("Error transmitting to Kafka topic", exception);
              }
          }
      });

      // flush data
      producer.flush();
      // flush and close producer
      producer.close();
    }

    public Properties  bootstrapProducer(String kafkaServer){
        Properties kafkaProps = new Properties();
        kafkaProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        kafkaProps.put(StreamsConfig.APPLICATION_ID_CONFIG,"change-events-poc");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return kafkaProps;
    }
}