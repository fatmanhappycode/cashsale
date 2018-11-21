package com.cashsale.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cashsale.bean.MessageDTO;

/**
 * @author Sylvia
 * 2018年11月3日
 */
public class SocketDAO {

	/** 每页显示的数据数目 */
	private static final int MESSAGE_NUMBER = 8;

	/**
	 * 获取历史记录
	 * @param senderName
	 * @param receiverName
	 * @param page
	 * @return
	 */
	public Map<String, Object> getMessage(String senderName, String receiverName, int page){
		List<MessageDTO> array = new ArrayList<MessageDTO>();
		Map<String,Object> map = new HashMap<>();
		Connection conn = new com.cashsale.conn.Conn().getCon();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		int noRead = 0;

		try {
			String query = "SELECT * FROM chat_history WHERE sender=? AND receiver=? ORDER BY date DESC LIMIT "+((page-1)*MESSAGE_NUMBER)+","+MESSAGE_NUMBER;
			pstmt.setString(1,senderName);
			pstmt.setString(2,receiverName);
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
			query = "SELECT COUNT(*) FROME chat_history WHERE is_read=? AND receiver=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setString(2,receiverName);
			result = pstmt.executeQuery();
			if(result.next()) {
				noRead = result.getInt(1);
			}
			map.put("noRead", noRead);
			map.put(sender, array);
		}catch(Exception e) {
			e.printStackTrace();
		}
		new com.cashsale.conn.Conn().closeConn(result, pstmt, conn);
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
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement("INSERT INTO chat_history (sender,receiver,content,date,img_url,"
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

	/**
	 * 获取与该用户聊过天的人
	 * @param username
	 *      用户名
	 * @return
	 *      其它用户名列表
	 */
	public ArrayList<String> getPerson(String username){
		Connection conn = new com.cashsale.conn.Conn().getCon();
		ArrayList<String> array = new ArrayList<>();
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		System.out.println("查询联系人时username="+username);
		try {
			ptmt = conn.prepareStatement("SELECT sender, receiver FROM chat_history WHERE sender=? OR receiver=?");
			ptmt.setString(1,username);
			ptmt.setString(2,username);
			rs = ptmt.executeQuery();

			while(rs.next()) {
				String sender = rs.getString("sender");
				String receiver = rs.getString("receiver");
				if(!sender.equals(username) && array.indexOf(sender) == -1){
					array.add(sender);
				}else if(!receiver.equals(username) && array.indexOf(receiver) == -1){
					array.add(receiver);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("获取联系人失败！");
		}
		new com.cashsale.conn.Conn().closeConn(rs, ptmt, conn);
		return array;
	}
}
