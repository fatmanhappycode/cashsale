package com.cashsale.dao;

import com.cashsale.bean.MessageDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;

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
            pstmt = conn.prepareStatement("SELECT * FROM chat_history WHERE receiver=? AND is_read=1");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            MessageDTO message = new MessageDTO();
            List<MessageDTO> result = new ArrayList<>();
            while (rs.next()) {
                message.setContent(rs.getString("content"));
                message.setDate(rs.getString("date"));
                message.setSender(rs.getString("sender"));
                result.add(message);
            }
            return new ResultDTO<List<MessageDTO>>(124,result,"查询成功");
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
               return new ResultDTO<String>(124,"1","查询成功");
            }else {
                return new ResultDTO<String>(125, "0", "查询成功");
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
