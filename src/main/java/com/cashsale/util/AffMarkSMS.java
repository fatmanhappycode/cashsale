package com.cashsale.util;

import com.cashsale.config.MobileCodeConfig;

import java.net.URLEncoder;

/**
 * @Description:会员营销短信接口
 * @Author:
 * @Date: 2018/12/1
 */
public class AffMarkSMS {
    private static String operation = "/affMarkSMS/sendSMS";

    private static String accountSid = MobileCodeConfig.ACCOUNT_SID;
    private static String to = "1361305****";
    private static String smsContent = "【秒嘀科技】您的优惠券就快过期啦！不想白白浪费，就快来使用吧！戳： m.miaodiyun.com 使用！回TD退订。";

    /**
     * 会员营销短信
     */
    public static void execute()
    {
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){

        }
        String url = MobileCodeConfig.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }
}
