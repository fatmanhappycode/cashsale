package com.cashsale.enums;

import java.net.Inet4Address;

public enum ResultEnum {
    /** 请先登录 */
    PLEASE_LOGIN(332,"请先登录"),
    /** 请求失败 */
    ERROR(500,"注册失败"),

    /** 邮箱激活成功 */
    ACTIVE_SUCCESS(200, "激活成功！"),
    /** 邮箱激活超时 */
    ACTIVE_TIME_ERROR(408,"激活超时"),
    /** 验证码错误 */
    ACTIVE_ERROR(412,"验证码错误，请重新激活"),

    /** 改手机号已被注册 */
    REGISTER_NUMBER_ERROR(412,"该手机号已被注册"),
    /** 该邮箱已被注册 */
    REGISTER_MAIL_ERROR(412,"该邮箱已被注册"),
    /** 请到邮箱进行账号激活 */
    REGISTER_TO_MAIL(100,"请到邮箱进行账号激活"),

    /** 用户名错误 */
    LOGIN_USERNAME_ERROR(401,"用户名不存在"),
    /** 密码错误 */
    LOGIN_PASSWORD_ERROR(401,"密码错误"),
    /** 登录成功 */
    LOGIN_SUCCESS(200,"登录成功"),

    /** 发布成功 */
    PUBLISH_SUCCESS(200,"发布成功"),
    /** 包含敏感词汇 */
    PUBLISH_CONTENT_ERROR(409,"不能包含敏感词汇"),

    /** 没有更多数据了 */
    SCREEN_NO_DATA(404,"没有更多数据了"),
    /** 查询成功 */
    SEARCH_SUCCESS(200,"查询成功"),

    /** 成功获取历史联系人 */
    SOCKET_GET_PERSON(200,"成功获取历史联系人"),
    /** 链接发生错误 */
    SOCKET_ERROR(404,"链接发生错误"),
    
    ;

    /** 状态码 */
    private Integer code;
    /** 信息 */
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
