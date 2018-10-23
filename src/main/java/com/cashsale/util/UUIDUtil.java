package com.cashsale.util;

import java.util.UUID;

/**
 * 生成随机字符串
 * @author Sylvia
 * 2018年10月16日
 */
public class UUIDUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}