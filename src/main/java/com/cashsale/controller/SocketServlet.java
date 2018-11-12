package com.cashsale.controller;

import java.net.URLDecoder;
import java.util.*;
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

	public static List<Session> sessions = new ArrayList<Session>();
	public static List<String> names = new ArrayList<String>();

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

		this.names.add(username);
		this.sessions.add(session);
		System.out.println("username="+username);
		System.out.println("queryString="+queryString);
	}

	@OnMessage
	public void message(Session session, String json){
		/*MessageDTO msg = gson.fromJson(json, MessageDTO.class);
		//System.out.println("从前台获取的msg="+msg);

		//获取接收者
		System.out.println("接收者="+msg.getReceiver());
		String to = msg.getReceiver();
		//System.out.println("msg.getSender="+msg.getSender());
		//System.out.println("this.username="+this.username);
		Session toSession = SocketServlet.map.get(to);
		String tableName = msg.getSender() + " and " + to;
		try {
			//判断用户是否登录
			if(toSession != null && toSession.isOpen()) {
				//发送信息
				toSession.getAsyncRemote().sendText(JSONObject.toJSONString(msg));
				//保存聊天记录
				new SocketDAO().saveMessage(msg, false);
			}else {
				//System.out.println("保存离线消息");
				new SocketDAO().saveMessage(msg, true);
			}

			//System.out.println(JSONObject.toJSON(msg));
		} catch (Exception e) {
			sendMessage(402,"消息发送失败！");
			e.printStackTrace();
		}*/
        MessageDTO msg = gson.fromJson(json, MessageDTO.class);
		//获取系统当前时间
		String date = TimeUtil.getTime();
		msg.setDate(date);
		broadcast(sessions, JSONObject.toJSONString(msg));
	}

	@OnClose
	public void close(Session session) {

	    SocketServlet.map.clear();
	    sessions.remove(session);
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

	public void broadcast(List<Session> ss, String msg){
		for(Iterator iterator = ss.iterator(); iterator.hasNext();){
			Session session = (Session)iterator.next();
			try{
				session.getBasicRemote().sendText(msg);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}