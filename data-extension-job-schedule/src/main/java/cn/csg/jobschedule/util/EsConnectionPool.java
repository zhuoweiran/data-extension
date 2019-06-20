package cn.csg.jobschedule.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @Author: tide
 * @Description: ES连接池
 * @Date: Created in 17:26 2018/10/31
 * @Modified By:
 */
public class EsConnectionPool implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(EsConnectionPool.class);

    private static TransportClient client = null;
    private static EsConnectionPool instance = null ;

    /**
     * @return TransportClient
     */
    public EsConnectionPool(String nodes, int port, String clusterName) {
        if (client == null) {
            String[] nds = nodes.split(",");
            try {
                client = new PreBuiltTransportClient(initSettings(clusterName));
                for (String nd : nds) {
                    client.addTransportAddress(new TransportAddress(InetAddress.getByName(nd), port));
                }
            } catch (Exception e) {
                logger.error("es连接异常:" + e.getMessage());
            }
        }
    }

    /**
     * 初始化参数设置
     * @return Settings
     */
    public static Settings initSettings(String clusterName) {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                //探测集群中机器状态
                .put("client.transport.sniff", true)
                .build();
        return settings;
    }

    public static synchronized EsConnectionPool getInstance(String nodes,int port,String clusterName) {
        if (instance == null) {
            instance = new EsConnectionPool(nodes, port, clusterName);
        }
        return instance;
    }

    public TransportClient getClient() {
        return client;
    }

    public void setClient(TransportClient client) {
        this.client = client;
    }

    /**
     * 释放ES连接
     * @param client
     */
    public void release(TransportClient client) {
        client.close();
    }
}
