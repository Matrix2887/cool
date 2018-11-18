package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {

    // 总记录数 根据数据库查询出来 count
    private int totalCount;

    // 总页数 根据总记录数/每页显示的条数 计算出来
    private int totalPage;

    // 当前页码 根据前端页面接收回来
    private int currentPage;

    // 每页显示的条数 根据前端页面接收回来
    private int pageSize;

    // 每页显示的数据集合 根据limit进行计算 计算公式为 (当前页码 - 1) * 每页显示的条数
    private List<T> list;

    public PageBean() {
    }

    public PageBean(int totalCount, int totalPage, int currentPage, int pageSize, List<T> list) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
