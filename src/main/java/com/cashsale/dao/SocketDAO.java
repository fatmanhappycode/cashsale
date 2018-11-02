package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cashsale.bean.Message;

public class SocketDAO {
	
	/** 暂无更多消息的code */
	private static final int NO_MORE_MESSAGE = 403;
	/** 每页显示的数据数目 */
	static int MESSAGE_NUMBER = 8;
	/** 查询成功 */
	private static final int QUERY_SUCCESSED = 200;
	
	/** 判断是否存在该表，不存在则创建 
	 * @param tableName
	 */
	public void creatTable(String tableName) {
		Connection conn = new com.cashsale.conn.Conn().getCon();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, tableName, null);
	        if (rs.next()) {
	             
	        }
	        else {
	        	stmt.execute("CREATE TABLE "+ tableName + "(sender VARCHAR(20) NOT NULL PRIMARY KEY, receiver "
	        		+ "VARCHAR(20) NOT NULL, content VARCHAR(255), date CHAR(20) NOT NULL, img_url VARCHAR(255),"
	        		+ " is_read BOOLEAN DEFAULT false, is_offline BOOLEAN DEFAULT true)");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库创建失败！");
		}
		new com.cashsale.conn.Conn().closeConn(rs, stmt, conn);
	}
	
	/**
	 * 获取离线消息
	 * @param tableName
	 * 			谁和谁的聊天记录
	 * @return
	 */
	public Map<String, Object> getOfflineMsg(String tableName, String strPage) {
		ArrayList<Message> array = new ArrayList<Message>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", NO_MORE_MESSAGE);
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int page = 1 ;
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		try {
			String query = "SELECT * FROM " + tableName +" WHERE is_offline = true ORDER BY date LIMIT "
					+((page-1)*MESSAGE_NUMBER)+","+MESSAGE_NUMBER;;
			pstmt = conn.prepareStatement(query);
			result = pstmt.executeQuery();
			while(result.next()) {
				String sender = result.getString("sender");
				String content = result.getString("content");
				String date = result.getString("date");
				String imgUrl = result.getString("img_url");
				Message msg = new Message(sender,null,content,date,imgUrl);
				array.add(msg);
			}
			map.put("messageRow", array.size());
			map.put("message", array);
			map.put("code", QUERY_SUCCESSED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
		return map;
	}
	
	/** 获取历史记录
	 * @param tableName
	 * @param strPage
	 * @return
	 */
	public Map<String, Object> getMessage(String tableName, String strPage){
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Message> array = new ArrayList<Message>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int page = 1 ;
		map.put("code", NO_MORE_MESSAGE);
		Connection conn = new com.cashsale.conn.Conn().getCon();
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		try {
			String query = "SELECT * FROM " + tableName + "LIMIT "+((page-1)*MESSAGE_NUMBER)+","+MESSAGE_NUMBER;
			pstmt = conn.prepareStatement(query);
			result = pstmt.executeQuery();
			while(result.next()) {
				String sender = result.getString("sender");
				String content = result.getString("content");
				String date = result.getString("date");
				String imgUrl = result.getString("img_url");
				Message msg = new Message(sender,null,content,date,imgUrl);
				array.add(msg);
			}
			map.put("message", array);
			map.put("code",QUERY_SUCCESSED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 把聊天记录保存到数据库中
	 * @param tableName
	 * @param message
	 * @param isOffline
	 */
	public void saveMessage(String tableName, Message message,boolean isOffline) {
		Connection conn = new com.cashsale.conn.Conn().getCon();
		String sender = message.getSender();
		String receiver = message.getReceiver();
		String date = message.getDate();
		String imgUrl = message.getImgUrl();
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		creatTable(tableName);
		try {
			ptmt = conn.prepareStatement("INSERT INTO "+tableName+"(sender,receiver,date,imgurl,"
				+ "is_offline) VALUES(?,?,?,?,?)");
			ptmt.setString(1, sender);
			ptmt.setString(2, receiver);
			ptmt.setString(3, date);
			ptmt.setString(4, imgUrl);
			ptmt.setBoolean(5, isOffline);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据保存失败！");
		}
		new com.cashsale.conn.Conn().closeConn(rs, ptmt, conn);
	}
}
