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
@Data
@AllArgsConstructor
@ToString
public class File implements Serializable {

    private static final long serialVersionUID = -6956947981866795431L;

    private Long id;
    private String name;
    private String md5;
    private String size;
    private Date uploadTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public static final class FileBuilder {
        private Long id;
        private String name;
        private String md5;
        private String size;
        private Date uploadTime;

        private FileBuilder() {
        }

        public static FileBuilder builder() {
            return new FileBuilder();
        }

        public FileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FileBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FileBuilder md5(String md5) {
            this.md5 = md5;
            return this;
        }

        public FileBuilder size(String size) {
            this.size = size;
            return this;
        }

        public FileBuilder uploadTime(Date uploadTime) {
            this.uploadTime = uploadTime;
            return this;
        }

        public File build() {
            return new File(id, name, md5, size, uploadTime);
        }
    }


}
