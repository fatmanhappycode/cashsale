package com.cashsale.util;

import com.cashsale.config.MobileCodeConfig;

/**
 * @Description:获取开发者账号信息接口调用示例
 * @Author: 8-0416
 * @Date: 2018/12/1
 */
public class AccountInfo {
    private static String operation = "/query/accountInfo";

    private static String accountSid = MobileCodeConfig.ACCOUNT_SID;

    /**
     * 获取开发者账号信息
     */
    public static void execute()
    {
        String url = MobileCodeConfig.BASE_URL + operation;
        String body = "accountSid=" + accountSid + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }
}
