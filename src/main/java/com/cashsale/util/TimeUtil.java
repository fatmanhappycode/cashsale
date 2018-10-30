package com.cashsale.util;

import java.util.Calendar;

/**
 * 时间工具
 * @author Sylvia
 * 2018年10月20日
 */
public class TimeUtil {

	/**
	 * 获取时间，返回毫秒
	 * @return
	 */
	public static String getTime() {
		
		Calendar calendar = Calendar.getInstance();
		// 获取毫秒时间
		Long date = calendar.getTime().getTime(); 

		return date.toString();
	}

	/**
	 * 邮箱验证的时间<br>
	 * 判断验证时间是否超过五分钟
	 * @param time
	 * 			生成验证码的时间
	 * @return
	 */
	public static boolean emailTime(String time) {
		
		long tempTime = Long.parseLong(time);

		// 在获取现在的时间
		Calendar calendar = Calendar.getInstance();
		// 获取毫秒时间
		Long date = calendar.getTime().getTime(); 

		// 5分钟 （300000）
		if (date - tempTime > 300000) 
		{ 
			return false;
		} 
		else 
		{
			return true;
		}

	}
}