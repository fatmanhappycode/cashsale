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

    public PagerDTO(int currentPage, List<T> data) {
        this.currentPage = currentPage;
        this.data = data;
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
}
