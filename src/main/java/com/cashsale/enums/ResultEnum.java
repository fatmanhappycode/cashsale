package com.cashsale.enums;

import java.net.Inet4Address;

public enum ResultEnum {
    /** 请先登录 */
    PLEASE_LOGIN(332,"请先登录"),
    /** 请求失败 */
    ERROR(500,"请求失败"),
    /** 请求成功 */
    SUCCESS(200,"请求成功"),
    /** 该商品不存在 */
    NO_FOUND(401,"该商品不存在"),

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
    LOGIN_USERNAME_ERROR(401,"该用户不存在"),
    /** 密码错误 */
    LOGIN_PASSWORD_ERROR(401,"密码错误"),
    /** 登录成功 */
    LOGIN_SUCCESS(200,"登录成功"),
    /** 用户名或密码错误 */
    LOGIN_ERROR(401,"登录失败，用户名或密码错误"),
    /** 验证码错误 */
    VERIFYCODE_ERROR(401,"验证码错误"),
    /** 验证码正确 */
    VERIFYCODE_RIGHT(200,"验证码正确"),

    /** 发布成功 */
    PUBLISH_SUCCESS(200,"发布成功"),
    /** 包含敏感词汇 */
    PUBLISH_CONTENT_ERROR(409,"不能包含敏感词汇"),
    /** 无权发布捐赠 */
    NO_ENTITLED_TO_PUBLISH(403,"您无权发布捐赠信息"),

    /** 没有更多数据了 */
    NO_MORE_DATA(404,"没有更多数据了"),
    /** 查询成功 */
    SEARCH_SUCCESS(200,"查询成功"),

    /** 成功获取历史联系人 */
    SOCKET_GET_PERSON(200,"成功获取历史联系人"),
    /** 链接发生错误 */
    SOCKET_ERROR(404,"链接发生错误"),

    /** 查询个人信息成功 */
    PERSON_SUCCESS(200,"成功获取个人信息"),

    /** 推荐成功 */
    RECOMMEND_SUCCESS(200,"推荐成功"),

    /** 加入购物车成功 */
    ADD_TROLLEY_SUCCESS(200,"成功加入购物车"),
    /** 该商品已存在购物车中 */
    ALREADY_IN_TROLLEY(401,"该商品已存在购物车中"),
    /** 该商品不在购物车中 */
    NOT_IN_TROLLEY(412,"该商品不在购物车中"),

    /** 更新用户信用或商品评分成功 */
    SCORE_SUCCESS(200,"成功更新评分"),

    /** 认证失败，密码或学号错误 */
    CONFIRM_ERROR(401,"密码或学号错误"),
    /** 认证成功 */
    CONFIRM_SUCCESS(200,"认证成功"),
    /** 已认证 */
    ALREADY_CONFIRM(401,"已认证"),
    /** 未认证 */
    NOT_CONFIMR(412,"未认证"),

    /** 密码修改成功 */
    PASSWORD_SUCCESS(200,"密码修改成功"),
    /** 重置密码邮件发送成功 */
    SET_PASSWORD_EMAIL(200,"重置密码邮件发送成功，请到邮箱进行确认"),
    /** 用户名或邮箱错误 */
    SET_PASSWORD_ERROR(401, "用户名或邮箱错误"),

    /** 交易成功 */
    TRANSACT_SUCCESS(200,"交易成功"),

    /** 评论商品成功 */
    COMMENT_SUCCESS(200,"评论成功"),

    /** 已点赞该商品 */
    ALREADY_LIKE(401,"已点赞"),
    /** 未点赞 */
    NOT_LIKE(412,"未点赞"),

    /** 关注成功 */
    CONCERN_SUCCESS(200,"关注成功"),
    /** 已关注 */
    ALREADY_CONCERN(401,"已关注"),
    /** 未关注 */
    NOT_CONCERN(412, "未关注"),

    /** 属于组织 */
    IS_ORGANIZATION(201,"属于组织")
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
