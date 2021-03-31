package com.zf1976.myuploader;

import com.zf1976.uploader.UploaderApplication;
import com.zf1976.uploader.dao.FileDao;
import com.zf1976.uploader.service.FileService;
import com.zf1976.uploader.utils.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UploaderApplication.class)
public class MyUploaderApplicationTests {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileService service;

    @Test
    public void contextLoads() {
        final String uploadPath = FileUtil.getUploadPath();
        final File uploadFile = Paths.get(uploadPath).toFile();
        for (File file : Objects.requireNonNull(uploadFile.listFiles())) {
            if (file.delete()) {
                System.out.println(file.getName());
            }
        }

    }

    public static java.io.File createFile(String filename) {
        final String uploadPath = FileUtil.getUploadPath();
        final java.io.File file = Paths.get(uploadPath, filename).toFile();
        if (!file.exists() || file.isDirectory()) {
            throw new RuntimeException("this file : " + filename +"is directory");
        }
        return file;
    }

    /**
     * 生成新文件名
     *
     * @param rawFilename 源
     * @param md5 文件md5
     * @return filename
     */
    public static String generatedMd5Filename(String rawFilename, String md5) {
        Assert.notNull(rawFilename,"origin filename cannot been null");
        return md5 + rawFilename.substring(rawFilename.indexOf("."));
    }
}
