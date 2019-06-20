package cn.csg.msg.producer.config;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class KafkaProducerSource {
    @Value("${kafka.bigdata.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${kafka.producer.client.id}")
    private String clientId;

    @Value("${kafka.producer.batch.size}")
    private int batchSize;

    @Value("${kafka.producer.linger.ms}")
    private int lingerMs;

    @Value("${kafka.producer.buffer.memory}")
    private String bufferMemory;

    @Value("${kafka.producer.request.size}")
    private int requestSize;

    @Bean
    public KafkaProducer initKafkaProducer(){
        Map<String, Object> config = Maps.newHashMap();
        config.put("bootstrap.servers", bootstrapServers);
        config.put("key.serializer", StringSerializer.class.getName());
        config.put("value.serializer", StringSerializer.class.getName());
        config.put("client.id", clientId) ;
        config.put("batch.size", batchSize) ;
        config.put("linger.ms", lingerMs) ;
        config.put("buffer.memory", bufferMemory) ;
        config.put("acks", "all") ;
        config.put("kafka.producer.request.size", requestSize) ;

        return new KafkaProducer<String, String>(config);
    }
}
