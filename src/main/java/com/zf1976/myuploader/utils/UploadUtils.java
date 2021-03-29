package com.zf1976.myuploader.utils;

import java.util.HashMap;
import java.util.Map;

import static com.zf1976.myuploader.utils.FileUtils.generateFileName;

/**
 * 分块上传工具类
 * @author mac
 */
public class UploadUtils {
    /**
     * 内部类记录分块上传文件信息
     */
    private static class Value {
        String name;
        boolean[] status;

        Value(int n) {
            this.name = generateFileName();
            this.status = new boolean[n];
        }
    }

    private static final Map<String, Value> CHUNK_MAP = new HashMap<>();

    /**
     * 判断文件所有分块是否已上传
     * @param key
     * @return
     */
    public static boolean isUploaded(String key) {
        if (isExist(key)) {
            for (boolean b : CHUNK_MAP.get(key).status) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否有分块已上传
     * @param key
     * @return
     */
    private static boolean isExist(String key) {
        return CHUNK_MAP.containsKey(key);
    }

    /**
     * 为文件添加上传分块记录
     * @param key
     * @param chunk
     */
    public static void addChunk(String key, int chunk) {
        CHUNK_MAP.get(key).status[chunk] = true;
    }

    /**
     * 从map中删除键为key的键值对
     * @param key
     */
    public static void removeKey(String key) {
        if (isExist(key)) {
            CHUNK_MAP.remove(key);
        }
    }

    /**
     * 获取随机生成的文件名
     * @param key
     * @param chunks
     * @return
     */
    public static String getFileName(String key, int chunks) {
        if (!isExist(key)) {
            synchronized (UploadUtils.class) {
                if (!isExist(key)) {
                    CHUNK_MAP.put(key, new Value(chunks));
                }
            }
        }
        return CHUNK_MAP.get(key).name;
    }
}
