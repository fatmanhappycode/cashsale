package com.cashsale.config;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

/**
 * WebSocket 的核心配置类</br>
 * （项目启动时会自动启动）
 * @author Sylvia
 * 2018年10月28日
 */
public class SocketConfig implements ServerApplicationConfig {

	/**
	 * 扫描src下所有类（@ServerEndPoint注解的类）</br>
	 * EndPoint 指的是一个webSocket的一个服务端程序
	 * @param scan
	 * @return
	 */
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scan) {
		// TODO Auto-generated method stub
		System.out.println("config……" + scan.size());
		//返回 提供了过滤的作用
		return scan;
	}

	/**
	 * 获取所有以接口方式配置的webSocket类
	 * @param arg0
	 * @return
	 */
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}