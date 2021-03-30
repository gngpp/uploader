package com.zf1976.uploader.dao;

/**
 * @author ant
 * Create by Ant on 2021/3/30 6:59 PM
 */
public class PageUtil {

    /**
     * 总数
     */
    private Integer totalRecord = -1;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页数
     */
    private Integer pageCount;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public PageUtil setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageUtil setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public PageUtil setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
