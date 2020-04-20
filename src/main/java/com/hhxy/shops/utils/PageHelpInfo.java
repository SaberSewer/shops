package com.hhxy.shops.utils;

import java.util.List;

public class PageHelpInfo {
    private Long now;
    private Long limitNum = 10L; //默认一页显示10条
    private List list;//存放结果集
    private Long pageNum;//总分页的页数
    private Long count;//条数
    private StringBuffer url;
    private Long nowLine;
    private Long endLine;

    /**
     *
     * @param list 查询拿到的List
     * @param access 访问路径
     */
    public PageHelpInfo(List list, String access) {
        this.list = list;
        this.url = new StringBuffer(access);
    }

    public PageHelpInfo(){

    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        if(now == 1){
            this.now = 0L;
        } else {
            this.now = now - 1;
        }
        this.nowLine = this.now * limitNum;
        this.endLine = nowLine + limitNum;
    }

    public Long getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Long limitNum) {
        this.limitNum = limitNum;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public StringBuffer getUrl() {
        return url;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
        this.pageNum = (count % limitNum) == 0 ? (count / limitNum) : (count / limitNum + 1);
    }

    public Long getPageNum() {
        return pageNum;
    }

    @Override
    public String toString() {
        return "PageHelpInfo{" +
                "now=" + now +
                ", limitNum=" + limitNum +
                ", list=" + list +
                ", pageNum=" + pageNum +
                ", count=" + count +
                ", url=" + url +
                ", nowLine=" + nowLine +
                ", endLine=" + endLine +
                '}';
    }
}
