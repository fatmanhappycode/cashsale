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
import com.cashsale.bean.Message;
import com.cashsale.bean.Result;
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
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，
	//其中Key可以为用户标识 
	@SuppressWarnings("rawtypes")
	private static CopyOnWriteArraySet<Map> webSocketSet = new CopyOnWriteArraySet<Map>();
	// private static List<Session> session = new ArrayList<Session>();
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

		//map.put(this.username, session);

		// this.session.add(session);
		// String msg = "";
		// this.broadcast(this.session, msg);
	}

	@OnMessage
	public void message(Session session, String json){
		Message msg = new JSONObject().getObject(json, Message.class);
		//设置发送时间
		msg.setDate(new TimeUtil().getCurrentTime());
		//System.out.println(JSONObject.toJSON(msg));
		//获取接收者
		String to = msg.getReceiver();
		Session toSession = SocketServlet.map.get(to);
		//System.out.println(to);

		try {
			toSession.getAsyncRemote().sendText(JSONObject.toJSONString(msg));
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
		Result<Object> result = new Result<Object>(code, null, message);
		this.session.getAsyncRemote().sendText(JSONObject.toJSONString(result)); 
	}

/*	public void broadcast(List<Session> ss, String msg) {
		for (Iterator<Session> iterator = ss.iterator(); iterator.hasNext();) {
			Session session = (Session) iterator.next();
			try {
				session.getAsyncRemote().sendText(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	/*
	 * //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。 private static int onlineCount = 0;
	 * 
	 * //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，
	 * 其中Key可以为用户标识 private static CopyOnWriteArraySet<SocketServlet> webSocketSet =
	 * new CopyOnWriteArraySet<SocketServlet>();
	 * 
	 * //与某个客户端的连接会话，需要通过它来给客户端发送数据 private Session session;
	 * 
	 *//**
		 * 连接建立成功调用的方法
		 * 
		 * @param session
		 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
		 * 
		 * @OnOpen public void onOpen(Session session){ this.session = session;
		 *         webSocketSet.add(this); //加入set中 addOnlineCount(); //在线数加1
		 *         System.out.println("有新连接加入！当前在线人数为" + getOnlineCount()); }
		 * 
		 *         /** 连接关闭调用的方法
		 */
	/*
	 * @OnClose public void onClose(){ webSocketSet.remove(this); //从set中删除
	 * subOnlineCount(); //在线数减1 System.out.println("有一连接关闭！当前在线人数为" +
	 * getOnlineCount()); }
	 * 
	 *//**
		 * 收到客户端消息后调用的方法
		 * 
		 * @param message
		 *            客户端发送过来的消息
		 * @param session
		 *            可选的参数
		 */
	/*
	 * @OnMessage public void onMessage(String message, Session session) {
	 * System.out.println("来自客户端的消息:" + message); //群发消息 for(SocketServlet item:
	 * webSocketSet){ try { item.sendMessage(message); } catch (IOException e) {
	 * e.printStackTrace(); continue; } } }
	 * 
	 *//**
		 * 发生错误时调用
		 * 
		 * @param session
		 * @param error
		 */
	/*
	 * @OnError public void onError(Session session, Throwable error){
	 * System.out.println("发生错误"); error.printStackTrace(); }
	 * 
	 *//**
		 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
		 * 
		 * @param message
		 * @throws IOException
		 *//*
			 * public void sendMessage(String message) throws IOException{
			 * this.session.getAsyncRemote().sendText(message);
			 * //this.session.getAsyncRemote().sendText(message); }
			 * 
			 * public static synchronized int getOnlineCount() { return onlineCount; }
			 * 
			 * public static synchronized void addOnlineCount() {
			 * SocketServlet.onlineCount++; }
			 * 
			 * public static synchronized void subOnlineCount() {
			 * SocketServlet.onlineCount--; }
			 */
}