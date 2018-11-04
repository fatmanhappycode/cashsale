package com.cashsale.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.MessageDTO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.SocketDAO;
import com.cashsale.util.TimeUtil;
import com.google.gson.Gson;

/**
 * 私聊
 * @author Sylvia 
 * 2018年10月28日
 */
@ServerEndpoint("/socket")
public class SocketServlet {

	private String username;
	private Session session;
	@SuppressWarnings("rawtypes")
	private static CopyOnWriteArraySet<Map> webSocketSet = new CopyOnWriteArraySet<Map>();
	private static Map<String, Session> map = new HashMap<String, Session>();
	static Gson gson = new Gson();

	@OnOpen
	public void open(Session session) throws Exception {
		System.out.println("启动成功:");
		this.session = session;
		
		//getQueryString 方法返回请求行中的参数部分。
		String queryString = session.getQueryString();
		// 解码queryString
		queryString = URLDecoder.decode(queryString, "utf-8");
		System.out.println(queryString);
		username = queryString.split("=")[1];
		
		map.put(username, session);
		webSocketSet.add(map);
	}

	@OnMessage
	public void message(Session session, String json){
		MessageDTO msg = new JSONObject().getObject(json, MessageDTO.class);
		//设置发送时间
		msg.setDate(new TimeUtil().getCurrentTime());
		//System.out.println(JSONObject.toJSON(msg));
		//获取接收者
		String to = msg.getReceiver();
		Session toSession = SocketServlet.map.get(to);
		//System.out.println(to);
		String tableName = msg.getSender() + " and " + to;
		try {
			//判断用户是否登录
			if(toSession.isOpen()) {
				toSession.getAsyncRemote().sendText(JSONObject.toJSONString(msg));
				//保存聊天记录
				new SocketDAO().saveMessage(msg, false);
			}else {
				new SocketDAO().saveMessage(msg, true);
			}
			
			//System.out.println(JSONObject.toJSON(msg));
		} catch (Exception e) {
			sendMessage(402,"消息发送失败！");
			e.printStackTrace();
		}

	}

	@OnClose
	public void close(Session session) {
		SocketServlet.map.clear();
	}
	
	@OnError 
	public void onError(Session session, Throwable error){
		//System.out.println("发生错误"); 
		error.printStackTrace();
		sendMessage(401,"连接发生错误！");
	}
	
	/** 返回错误信息 */
	public void sendMessage(int code, String message){
		//this.session.getAsyncRemote().sendText(message);
		ResultDTO<Object> result = new ResultDTO<Object>(code, null, message);
		this.session.getAsyncRemote().sendText(JSONObject.toJSONString(result)); 
	}

}