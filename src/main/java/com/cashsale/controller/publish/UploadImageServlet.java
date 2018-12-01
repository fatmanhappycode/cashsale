package com.cashsale.controller.publish;

import com.alibaba.fastjson.JSONObject;
import com.cashsale.bean.ImageDTO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebServlet("/UploadImageServlet")
public class UploadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断上传表单是否为multipart/form-data类型
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				//1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
				DiskFileItemFactory factory = new DiskFileItemFactory();
				/*File fileUrl = new File("e:");
				factory.setRepository(fileUrl);*/
				//System.out.println(System.getProperty("java.io.tmpdir"));//默认临时文件夹
				File fileUrl = new File("C:\\Study");
				factory.setRepository(fileUrl);

				//2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
				ServletFileUpload sfu = new ServletFileUpload(factory);
				//以byte为单位    不能超过10M  1024byte = 1kb  1024kb=1M 1024M = 1G
				sfu.setSizeMax(10 * 1024 * 1024);
				sfu.setHeaderEncoding("utf-8");

				//3. 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
				List<FileItem> fileItemList = sfu.parseRequest(request);
				Iterator<FileItem> fileItems = fileItemList.iterator();

				//4. 遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
				ArrayList<String> data = new ArrayList<>();
				while (fileItems.hasNext()) {
					FileItem fileItem = fileItems.next();
					//普通表单元素
					if (fileItem.isFormField()) {
						//name属性值
						String name = fileItem.getFieldName();
						//name对应的value值
						String value = fileItem.getString("utf-8");
						System.out.println(name + " = " + value);
					}
					//<input type="file">的上传文件的元素
					else {
						//文件名称
						String fileName = fileItem.getName();
						//Koala.jpg
						System.out.println("原文件名：" + fileName);
						String suffix = fileName.substring(fileName.lastIndexOf('.'));
						//.jpg
						System.out.println("扩展名：" + suffix);
						//新文件名（唯一）
						String newFileName = new Date().getTime() + suffix;
						//image\1478509873038.jpg
						System.out.println("新文件名：" + newFileName);

						//5. 调用FileItem的write()方法，写入文件
						//File file = new File(request.getServletContext().getRealPath("image") + "\\" + newFileName);
						File file = new File("E:\\javawork2\\cashsaleResource\\cashsale\\image" + "\\" + newFileName);
						System.out.println(file.getAbsolutePath());
						fileItem.write(file);

						//6. 调用FileItem的delete()方法，删除临时文件
						fileItem.delete();

						/*
						 * 存储到数据库时注意
						 *     1.保存源文件名称   Koala.jpg
						 *  2.保存相对路径      image/1478509873038.jpg
						 *
						 */
						data.add("image/" + newFileName);
					}
				}
				ImageDTO image = new ImageDTO();
				image.setErrno(0);
				image.setData(data);
				PrintWriter writer = response.getWriter();
				writer.print((JSONObject.toJSON(image)));
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}