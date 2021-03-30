package com.zf1976.uploader.utils;


import com.zf1976.uploader.config.UploadConfig;
import com.zf1976.uploader.model.ChunkFile;
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
public class FileUtil {

    /**
     * 用户目录
     */
    private static final String ROOT_PATH = System.getProperty("user.home");

    /**
     * 更好的uuid生成工具
     */
    private static final AlternativeJdkIdGenerator JDK_ID_GENERATOR = new AlternativeJdkIdGenerator();

    /**
     * 写入文件
     *
     * @param fileName 文件
     * @param fileInputStream 文件流
     * @throws IOException exception
     */
    public static void write(String fileName, InputStream fileInputStream) throws IOException {
        Path path = Paths.get(getUploadPath(), fileName);
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
            while ((len = fileInputStream.read(data)) != -1) {
                bufferedOutputStream.write(data,0, len);
            }
        }
    }

    /**
     * 文件根据块写入
     *
     * @param filename 文件名
     * @param chunkFile 块文件
     * @param chunkInputStream 块流
     * @return boolean
     */
    public static boolean writeWithChunk(String filename, ChunkFile chunkFile, InputStream chunkInputStream){
        // 目标文件
        File targetFile = createFile(filename);
        // rw表示支持读写
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
             BufferedInputStream bufferedInputStream = new BufferedInputStream(chunkInputStream)
        ) {
            // 设置随机读写文件大小
            randomAccessFile.setLength(chunkFile.getFileSize());
            // 块索引==最后索引
            if (chunkFile.getChunkIndex() == (chunkFile.getChunkTotal()) -1) {
                // 文件大小-块大小
                randomAccessFile.seek(chunkFile.getFileSize() - chunkFile.getChunkSize());
            } else {
                // 块索引*块大小
                randomAccessFile.seek(chunkFile.getChunkIndex() * chunkFile.getChunkSize());
            }
            // 无脑16k
            byte[] data = new byte[16*1024];
            int len ;
            while ((len = bufferedInputStream.read(data)) != -1) {
                randomAccessFile.write(data, 0 ,len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成随机文件名
     *
     * @return random filename
     */
    public static String generateFileName() {
        return JDK_ID_GENERATOR.generateId().toString();
    }

    /**
     * 上传路径
     * @return path
     */
    public static String getUploadPath(){
        return ROOT_PATH + UploadConfig.catalog;
    }

    /**
     * 功能描述
     *
     * @date 2021-03-29 23:36:55
     * @param file 文件
     * @return {@link File}
     */
    private static File createFile(File file) {
        if (file.isDirectory()) {
            throw new RuntimeException("This is not a file");
        } else {
            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        throw new RuntimeException("create file error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 根据文件名创建文件
     *
     * @date 2021-03-29 23:37:21
     * @param filename 文件名
     * @return {@link File}
     */
    private static File createFile(String filename) {
        return createFile(Paths.get(getUploadPath(), filename).toFile());
    }
}
