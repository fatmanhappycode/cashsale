package com.cashsale.dao;

import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.enums.ResultEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author 肥宅快乐码
 * @date 2018/11/10 - 17:27
 */
public class DetailProductInfoDAO {

    Connection conn = new com.cashsale.conn.Conn().getCon();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ResultDTO<ProductDO> showDetailProduct(String product_id) {
        try {
            int productId = 0;
            if(product_id != null & !product_id.equals("")){
                productId = Integer.parseInt(product_id);
            }
            int commentNumber = new GetInteractDAO().getCommentNumber(productId);
            int likeNumber = new GetInteractDAO().getLikeNumber(productId);
            int shareNumber = new GetInteractDAO().getShareNumber(productId);
            pstmt = conn.prepareStatement("SELECT * FROM product_info WHERE product_id=?");
            pstmt.setString(1, product_id);
            rs = pstmt.executeQuery();
            ProductDO product = new ProductDO();
            if (rs.next()) {
                product.setTitle(rs.getString("title"));
                product.setLabel(rs.getString("label"));
                product.setPrice(rs.getDouble("price"));
                product.setTradeMethod(rs.getInt("trade_method"));
                product.setIsBargain(rs.getInt("is_bargain"));
                product.setPdDescription(rs.getString("product_description"));
                product.setImageUrl(rs.getString("image_url"));
                product.setUsername(rs.getString("user_name"));
                product.setCommentsNumber(commentNumber);
                product.setLikeNumber(likeNumber);
                product.setShareNumber(shareNumber);
            }
            return new ResultDTO<ProductDO>(ResultEnum.SEARCH_SUCCESS.getCode(),product,ResultEnum.SEARCH_SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO<ProductDO>(ResultEnum.ERROR.getCode(),null,ResultEnum.ERROR.getMsg());
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