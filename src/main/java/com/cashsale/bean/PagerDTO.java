package com.cashsale.bean;

import java.util.List;

/**
 * @author 肥宅快乐码
 * @date 2018/10/26 - 22:20
 */
public class PagerDTO<T>{
    /**
     * 当前第几页数据
     */
    private int currentPage;
    private List<T> data;
    private T data2;

    public PagerDTO(int currentPage, List<T> data) {
        this.currentPage = currentPage;
        this.data = data;
    }

    public PagerDTO(int currentPage, T data2) {
        this.currentPage = currentPage;
        this.data2 = data2;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public T getData2() {
        return data2;
    }

    public void setData2(T data2) {
        this.data2 = data2;
    }
}
