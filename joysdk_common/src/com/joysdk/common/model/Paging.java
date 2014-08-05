package com.joysdk.common.model;

import java.io.Serializable;
import java.util.List;


public class Paging<T> implements Serializable {

    private static final long serialVersionUID=1430572489470157367L;

    // 当前的page 数
    private int currentPage = 1;
    
    //总页数
    private int pageCount=0;
    
    // 每页需要显示的条数
    private int size = 10;
    
    // 结果总数
    private int totalCount;
    
    // 结果集
    private List<T> result;

    //排序列
    private String orderColumn;
    
    //排序方向
    private String order;
    
    // 查询的开始
    private int startNum=1;

    
    public int getCurrentPage() {
        return currentPage;
    }

    
    public void setCurrentPage(int currentPage) {
        this.currentPage=currentPage;
    }

    
    public int getSize() {
        return size;
    }

    
    public void setSize(int size) {
        this.size=size;
    }

    
    public int getTotalCount() {
        return totalCount;
    }

    
    public void setTotalCount(int totalCount) {
        this.totalCount=totalCount;
        this.pageCount=(totalCount/size) + (totalCount%size);
    }

    
    public List<T> getResult() {
        return result;
    }

    
    public void setResult(List<T> result) {
        this.result=result;
    }

    
    public String getOrderColumn() {
        return orderColumn;
    }

    
    public void setOrderColumn(String orderColumn) {
        this.orderColumn=orderColumn;
    }

    
    public String getOrder() {
        return order;
    }

    
    public void setOrder(String order) {
        this.order=order;
    }

    
    public int getStartNum() {
        return startNum-1;
    }

    
    public void setStartNum(int startNum) {
        this.currentPage=startNum;
        this.startNum=startNum;
    }


    
    public int getPageCount() {
        return pageCount;
    }


    
    public void setPageCount(int pageCount) {
        this.pageCount=pageCount;
    }
    
}
