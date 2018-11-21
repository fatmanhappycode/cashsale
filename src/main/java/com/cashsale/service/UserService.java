package com.cashsale.service;

import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.IsConfirmDAO;
import com.cashsale.dao.UserLoginDAO;
import com.cashsale.enums.ResultEnum;
import com.cashsale.util.SendPostUtil;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/10/28 - 16:15
 */
public class UserService {
    /**
     * 判断登录成功与否，成功返回带token的Result
     * @param userName
     * @param password
     * @return ResultDTO<String>
     */
    public ResultDTO<String> userLogin(String userName, String password) {
        String token = new UserLoginDAO().isLogin(userName,password);
        if (!token.equals("")) {
            return new ResultDTO<String>(ResultEnum.LOGIN_SUCCESS.getCode(), token, ResultEnum.LOGIN_SUCCESS.getMsg());
        } else {
            return new ResultDTO<String>(ResultEnum.LOGIN_ERROR.getCode(), null, ResultEnum.LOGIN_ERROR.getMsg());
        }
    }

    public ResultDTO<String> userComfirm(String encoded) {
        // 教务系统登录请求链接
        String url = "http://jwxt.gduf.edu.cn/jsxsd/xk/LoginToXk";
        Object[] params = new Object[]{"encoded"};
        Object[] values = new Object[]{encoded};
        List<NameValuePair> paramsList = SendPostUtil.getParams(params, values);
        int a = 0;
        try {
            a = SendPostUtil.sendPost(url,paramsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (a == 302) {

            return new ResultDTO<String>(ResultEnum.CONFIRM_SUCCESS.getCode(), null,ResultEnum.CONFIRM_SUCCESS.getMsg());
        }
        else if (a == 200) {
            return new ResultDTO<>(ResultEnum.CONFIRM_ERROR.getCode(), null,ResultEnum.CONFIRM_ERROR.getMsg());
        }
        else {
            return new ResultDTO<>(ResultEnum.ERROR.getCode(), null,ResultEnum.ERROR.getMsg());
        }
    }
    public ResultDTO<String> isConfirm(String userName) {
        Boolean isConfirm = new IsConfirmDAO().isConfirm(userName);
        if (isConfirm) {
            return new ResultDTO<String>(ResultEnum.ALREADY_CONFIRM.getCode(), "1", ResultEnum.ALREADY_CONFIRM.getMsg());
        } else {
            return new ResultDTO<String>(ResultEnum.NOT_CONFIMR.getCode(), "0", ResultEnum.NOT_CONFIMR.getMsg());
        }
    }
}
