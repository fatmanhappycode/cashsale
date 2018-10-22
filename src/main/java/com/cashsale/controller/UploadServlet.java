package com.cashsale.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.Result;
import com.cashsale.bean.Upload;
import com.google.gson.Gson;

/**
 * 上传
 * @author Sylvia
 * 2018年10月14日
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//设置响应编码
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//设置请求编码
		request.setCharacterEncoding("UTF-8");
		
        Connection conn = new com.cashsale.conn.Conn().getCon();
		
		BufferedReader br = request.getReader();
		String str, img = "";
		while((str =  br.readLine()) != null) {
			img += str;
		}
		//System.out.println(img);
		PrintWriter writer = response.getWriter();
		String realPath = request.getSession().getServletContext().getRealPath("img");
		//System.out.println(realPath);
		Upload up = new Gson().fromJson(img, Upload.class);
		//获取上传商品的id
		String productId = up.getProductId();
		String imgUrl = "";
		ArrayList<String> returnUri = new ArrayList<String>();
		
		try {
	        File f = new File(realPath);
	        //判断存放上传文件的目录是否存在（不存在则创建）
	        if(!f.exists()&&!f.isDirectory()){
	                f.mkdir();
	            }
			
			for(int i = 0; i < up.getImgUri().size(); i ++)
			{
				//获取图片地址
				String imgUri = up.getImgUri().get(i);
				// 获取图片的后缀名
				String suffix = imgUri.substring(imgUri.lastIndexOf(".") + 1);
				// 获取当前系统时间
				long date = System.currentTimeMillis();
				File originFile = new File(imgUri);
				String newFileName = "cashsale-img-" + date + "." + suffix;
				File destFile = new File(realPath, newFileName);

				//将文件复制到新的路径
				FileUtils.copyFile(originFile, destFile);
				returnUri.add("img\\" + newFileName);
				imgUrl += "img\\" + newFileName +";";
			}
			//System.out.println(imgUrl);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE product_info SET image_url = ? WHERE "
					+ "product_id = ?");
			pstmt.setString(1, imgUrl);
			pstmt.setString(2, productId);
			pstmt.execute();
			writer.println(JSONObject.toJSON(new Upload(returnUri, productId)));
		}
		catch (Exception e) {
			writer.println(JSONObject.toJSON(new Result<Object>(104, null, "图片上传失败!")));
		}
	}
	
}