package com.zf1976.uploader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * File表存储上传的文件信息
 * @author mac
 */
public class File implements Serializable {

    private static final long serialVersionUID = -6956947981866795431L;

    private Long id;
    private String name;
    private String md5;
    private String size;
    private Date uploadTime;

    public File() {

    }

    public File(Long id, String name, String md5, String size, Date uploadTime) {
        this.id = id;
        this.name = name;
        this.md5 = md5;
        this.size = size;
        this.uploadTime = uploadTime;
    }

    public Long getId() {
        return id;
    }

    public File setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public File setName(String name) {
        this.name = name;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public File setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public String getSize() {
        return size;
    }

    public File setSize(String size) {
        this.size = size;
        return this;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public File setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }
}
