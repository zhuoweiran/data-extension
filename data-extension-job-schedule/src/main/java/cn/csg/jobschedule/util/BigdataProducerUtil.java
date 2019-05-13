package cn.csg.jobschedule.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Properties;

/***
 * @Author: xushiyong
 * @Description Kafka 生产者
 * @Date: Created in 21:12 2018/11/6
 * @Modify By:
 **/
public class BigdataProducerUtil implements Serializable {
    private static BigdataProducerUtil instance = null;
    private static Producer<String, String> producer ;

    private BigdataProducerUtil(Properties props){
        props.put("bootstrap.servers", props.getProperty("kafka.bigdata.bootstrap.servers"));
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("client.id", props.getProperty("kafka.producer.client.id")) ;
        props.put("batch.size", props.getProperty("kafka.producer.batch.size")) ;
        props.put("linger.ms", props.getProperty("kafka.producer.linger.ms")) ;
        props.put("buffer.memory", props.getProperty("kafka.producer.buffer.memory")) ;
        props.put("acks", "all") ;
        props.put("kafka.producer.request.size", props.getProperty("kafka.producer.request.size")) ;

        this.producer = new KafkaProducer<String, String>(props);
    }

    public static BigdataProducerUtil getInstance(Properties props){    //对获取实例的方法进行同步
        if (instance == null){
            synchronized(BigdataProducerUtil.class){
                if (instance == null)
                    instance = new BigdataProducerUtil(props);
                }
            }
        return instance;
    }

    // 单条发送(topic,value)
    public void send(String topic,String value) {
        producer.send(new ProducerRecord<>(topic, value));
    }

    // 单条发送(topic,key,value)
    public void send(String topic,String key,String value) {
        producer.send(new ProducerRecord<>(topic, key, value));
    }

    // 批量发送
   //TODO

    public void shutdown() {
        producer.close();
    }

    public void flush(){
        producer.flush();
    }


}
