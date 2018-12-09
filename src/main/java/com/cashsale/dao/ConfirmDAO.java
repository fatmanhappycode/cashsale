package com.cashsale.dao;

import com.cashsale.bean.ResultDTO;
import com.cashsale.enums.ResultEnum;
import com.cashsale.util.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 肥宅快乐码
 * @date 2018/11/10 - 10:47
 */
public class ConfirmDAO {
    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;

    public boolean Confirm(String username, String sno) {
        try {
            pstmt = conn.prepareStatement("UPDATE user_data SET is_certificate = 1,sno = ? WHERE user_name=?");
            pstmt.setString(1,sno);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            //更新信用 +5分
            new UpdateCreditDAO().updateCredit(username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    /**
     * 手机验证
     * @param username
     * @return
     */
    public ResultDTO MobileConfirm(String username, int code){
        try{
            pstmt = conn.prepareStatement("UPDATE user_data SET mobile_certificate = 1 WHERE user_name = ?");
            pstmt.setString(1,username);
            pstmt.executeUpdate();
            //更新信用 +5分
            new UpdateCreditDAO().updateCredit(username);
            return new ResultDTO(ResultEnum.CONFIRM_SUCCESS.getCode(), code, ResultEnum.CONFIRM_SUCCESS.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO(ResultEnum.ERROR.getCode(), null, ResultEnum.ERROR.getMsg());
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
