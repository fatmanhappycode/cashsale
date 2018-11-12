package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cashsale.bean.MessageDTO;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class SocketDAO {

	/** 暂无更多消息的code */
	private static final int NO_MORE_MESSAGE = 403;
	/** 每页显示的数据数目 */
	static int MESSAGE_NUMBER = 8;
	/** 查询成功 */
	private static final int QUERY_SUCCESSED = 200;

	/** 判断是否存在该表，不存在则创建
	 * @param sender
	 * @param receiver
	 * @return tableName
	 * 			返回表名
	 */
	public String createTable(String sender, String receiver) {
		Connection conn = new com.cashsale.conn.Conn().getCon();
		ResultSet rs = null;
		Statement stmt = null;
		String tableName = "";
		try {
			stmt = conn.createStatement();tableName = sender + "_and_" + receiver;
			rs = conn.getMetaData().getTables(null, null, tableName, null);

			if( !rs.next() ) {
				tableName = receiver + "_and_" + sender;
				rs = conn.getMetaData().getTables(null, null, tableName, null);

				if( !rs.next()) {
					stmt.execute("CREATE TABLE "+ tableName + "(sender VARCHAR(20) NOT NULL, receiver "
							+ "VARCHAR(20) NOT NULL, content VARCHAR(255), date CHAR(20) NOT NULL, img_url VARCHAR(255),"
							+ " is_read BOOLEAN DEFAULT false) CHARACTER SET utf8 COLLATE utf8_general_ci;");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库创建失败！");
		}
		new com.cashsale.conn.Conn().closeConn(rs, stmt, conn);
		return tableName;
	}

	/**
	 * 获取离线消息
	 * @param tableName
	 * 			谁和谁的聊天记录
	 * @return
	 */
/*	public Map<String, Object> getOfflineMsg(String tableName, String strPage) {
		ArrayList<MessageDTO> array = new ArrayList<MessageDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = new com.cashsale.conn.Conn().getCon();
		map.put("code", NO_MORE_MESSAGE);
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
			String sender = "";
			while(result.next()) {
				sender = result.getString("sender");
				String receiver = result.getString("receiver");
				String content = result.getString("content");
				String date = result.getString("date");
				String imgUrl = result.getString("img_url");
				MessageDTO msg = new MessageDTO(sender,receiver,content,date,imgUrl);
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
	}*/

	/** 获取历史记录
	 * @param tableName
	 * @param strPage
	 * @return
	 */
	public Map<String, Object> getMessage(String tableName, String strPage){
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<MessageDTO> array = new ArrayList<MessageDTO>();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int noRead = 0;
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
			String sender = "";
			while(result.next()) {
				sender = result.getString("sender");
				String receiver = result.getString("receiver");
				String content = result.getString("content");
				String date = result.getString("date");
				String imgUrl = result.getString("img_url");
				MessageDTO msg = new MessageDTO(sender,receiver,content,date,imgUrl);
				array.add(msg);
			}
			//获取未读消息的数目
			query = "SELECT COUNT(*) FROME " + tableName + " WHERE is_read=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			result = pstmt.executeQuery();
			if(result.next()) {
				noRead = result.getInt(1);
			}
			map.put("noRead", noRead);
			map.put(sender, array);
			map.put("code",QUERY_SUCCESSED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 把聊天记录保存到数据库中
	 * @param message
	 * @param isRead
	 */
	public void saveMessage(MessageDTO message,boolean isRead) {
		//System.out.println("进入保存");
		Connection conn = new com.cashsale.conn.Conn().getCon();
		String receiver = message.getReceiver();
		String sender = message.getSender();
		String content = message.getContent();
		String imgUrl = message.getImgUrl();
		String date = message.getDate();
		String tableName = createTable(sender, receiver);
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement("INSERT INTO "+tableName+"(sender,receiver,content,date,img_url,"
					+ "is_read) VALUES(?,?,?,?,?,?)");
			ptmt.setString(1, sender);
			ptmt.setString(2, receiver);
			ptmt.setString(3, content);
			ptmt.setString(4, date);
			ptmt.setString(5, imgUrl);
			ptmt.setBoolean(6, isRead);
			ptmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据保存失败！");
		}
		new com.cashsale.conn.Conn().closeConn(rs, ptmt, conn);
	}

	/** 获取与该用户相关的历史记录的表名
	 * @param name
	 * 			用户名
	 * @return
	 */
	public ArrayList<String> getTable(String name){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		ArrayList<String> array = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SHOW tables";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			//遍历数据库中的所有表名，并判断表名中是否包含name字段
			while(rs.next()) {
				String tableName = rs.getString(1);
				int is = tableName.indexOf(name);
				if(is != -1) {
					array.add(tableName);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("获取表名失败！");
		}
		return array;
	}
}
