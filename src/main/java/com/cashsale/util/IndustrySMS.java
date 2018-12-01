package com.cashsale.util;

import com.cashsale.config.MobileCodeConfig;

import java.net.URLEncoder;

/**
 * @Description:验证码通知短信接口
 * @Author:
 * @Date: 2018/12/1
 */
public class IndustrySMS {
    private static String operation = "/industrySMS/sendSMS";

    private static String accountSid = MobileCodeConfig.ACCOUNT_SID;
    private static String to = "1361305****";
    private static String smsContent = "【现卖】尊敬的用户，您好，您的验证码为："+accountSid+"，请于3分钟内正确输入，如非本人操作，请忽略此短信。";

    /**
     * 验证码通知短信
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
