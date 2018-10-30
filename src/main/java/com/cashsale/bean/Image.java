package com.cashsale.bean;

import java.util.ArrayList;

/**
 * @author 肥宅快乐码
 * @date 2018/10/29 - 23:54
 */
public class Image {
    private int errno;
    private ArrayList<String> data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
