/*package com.cashsale.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UploadDAO {
	*//** 上传成功的标志 *//*
	private static final int UPLOAD_SUCCESSED = 1;
	*//** 上传失败的code *//*
	private static final int UPLOAD_FAILED = 104;
	
	public int upload(String imgUrl, String productId) {
		
		try {
			Connection conn = new com.cashsale.conn.Conn().getCon();
			//System.out.println(imgUrl);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE product_info SET image_url = ? WHERE "
					+ "product_id = ?");
			pstmt.setString(1, imgUrl);
			pstmt.setString(2, productId);
			pstmt.execute();
			//writer.println(JSONObject.toJSON(new Upload(returnUri, productId)));
			return UPLOAD_SUCCESSED;
		}
		catch (Exception e) {
			//writer.println(JSONObject.toJSON(new Result<Object>(104, null, "图片上传失败!")));
			e.printStackTrace();
			return UPLOAD_FAILED;
		}
	}
}
*/