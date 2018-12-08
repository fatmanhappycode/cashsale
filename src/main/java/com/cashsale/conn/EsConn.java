package com.cashsale.conn;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 肥宅快乐码
 * @date 2018/12/2 - 12:09
 */
public class EsConn {
    public static TransportClient client = null;
    public EsConn() throws UnknownHostException {
        // 指定es集群
        Settings settings = Settings.builder().put("cluster.name","my-application").build();

        // 创建访问es服务器的客户端
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("120.76.58.120"),9300));
    }
}
