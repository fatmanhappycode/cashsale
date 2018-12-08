package com.cashsale.dao;

import com.cashsale.bean.MessageDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.enums.ResultEnum;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/11/16 - 21:19
 */
public class GetMessageDAO {

    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ResultDTO<List<MessageDTO>> getMessage(String username) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM chat_history WHERE receiver=?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            pstmt = conn.prepareStatement("UPDATE chat_history SET is_read=0 WHERE receiver=?");
            pstmt.setString(1,username);
            pstmt.executeUpdate();
            List<MessageDTO> result = new ArrayList<>();
            while (rs.next()) {
                MessageDTO message = new MessageDTO();
                message.setContent(rs.getString("content"));
                message.setDate(rs.getString("date"));
                message.setSender(rs.getString("sender"));
                result.add(message);
            }
            return new ResultDTO<List<MessageDTO>>(ResultEnum.SEARCH_SUCCESS.getCode(),result,ResultEnum.SEARCH_SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultDTO<String> isMessage(String username) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM chat_history WHERE receiver=? AND is_read=1");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
               return new ResultDTO<String>(ResultEnum.SEARCH_SUCCESS.getCode(),"1",ResultEnum.SEARCH_SUCCESS.getMsg());
            }else {
                return new ResultDTO<String>(ResultEnum.SEARCH_SUCCESS.getCode(),"0",ResultEnum.SEARCH_SUCCESS.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
