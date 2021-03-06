package com.zf1976.uploader.dao;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ant
 * Create by Ant on 2021/3/30 6:59 PM
 */
@SuppressWarnings("rawtypes")
public class PageInfo<T> {

    /**
     * 总数
     */
    private Integer totalRecord = -1;

    /**
     * 总页数
     */
    private Integer totalPage = -1;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页数
     */
    private Integer pageCount;


    private Collection<T> data;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public PageInfo<T> setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        return this;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public PageInfo<T> setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageInfo<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public PageInfo<T> setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public Collection<T> getData() {
        return data;
    }

    public PageInfo<T> setData(Collection<T> data) {
        this.data = new ArrayList<>(data);
        return this;
    }
}
