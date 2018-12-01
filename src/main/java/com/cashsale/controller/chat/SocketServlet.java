package com.cashsale.controller.chat;

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
import com.cashsale.enums.ResultEnum;
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
		username = queryString.split("=")[1];

		map.put(username, session);
		webSocketSet.add(map);

		getPerson();
	}

	@OnMessage
	public void message(Session session, String json){
		MessageDTO msg = gson.fromJson(json, MessageDTO.class);

		//获取接收者
		String to = msg.getReceiver();
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
				new SocketDAO().saveMessage(msg, true);
			}
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
		error.printStackTrace();
		sendMessage(401,"连接发生错误！");
	}

	/** 返回错误信息 */
	public void sendMessage(int code, String message){
		ResultDTO<Object> result = new ResultDTO<Object>(code, null, message);
		this.session.getAsyncRemote().sendText(JSONObject.toJSONString(result));
	}

	/** 获取历史联系人 */
	public void getPerson(){
		ArrayList<String> array = new SocketDAO().getPerson(username);
		for(int i = 0; i < array.size(); i++){
			System.out.println(array.get(i)+"  ");
		}
		if(array.size() > 0){
			this.session.getAsyncRemote().sendText(JSONObject.toJSONString(new ResultDTO<ArrayList>(ResultEnum.SOCKET_GET_PERSON.getCode(),array,ResultEnum.SOCKET_GET_PERSON.getMsg())));
		}
		else{
			this.session.getAsyncRemote().sendText(JSONObject.toJSONString(new ResultDTO<ArrayList>(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg())));
		}
	}

}