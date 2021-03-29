package com.zf1976.myuploader.utils;


import com.zf1976.myuploader.config.UploadConfig;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 * @author mac
 */
public class FileUtils {

    private static final String ROOT_PATH = System.getProperty("user.home");
    public static String getUploadPath(){
        return ROOT_PATH + UploadConfig.catalog;
    }

    /**
     * 更好的uuid生成工具
     */
    private static final AlternativeJdkIdGenerator JDK_ID_GENERATOR = new AlternativeJdkIdGenerator();

    /**
     * 写入文件
     *
     * @param uploadPath
     * @param fileTarget 文件
     * @param inputStream 文件流
     * @throws IOException exception
     */
    public static void write(String uploadPath, String fileTarget, InputStream inputStream) throws IOException {
        Path path = Paths.get(uploadPath, fileTarget);
        File file = null;
        if (!Files.exists(path)) {
            file = Files.createFile(path).toFile();
        }
        Assert.notNull(file, "file cannot been null");
        try (OutputStream fileOutputStream = new FileOutputStream(file);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            // 无脑16k
            byte[] data = new byte[16*1024];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                bufferedOutputStream.write(data,0, len);
            }
        }
    }

    /**
     * 分块写入文件
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public static void writeWithBlok(String target, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(target,"rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1) {
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf,0,len);
        }
        randomAccessFile.close();
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String generateFileName() {
        return JDK_ID_GENERATOR.generateId().toString();
    }
}
