package com.zf1976.uploader.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分块上传工具类
 * @author mac
 */
public class ChunkRecordUtil {

    /**
     * 文件块记录映射
     */
    private static final Map<String, Record> CHUNK_RECORD_MAP = new ConcurrentHashMap<>();

    /**
     * 判断文件所有分块是否已上传
     *
     * @param md5 文件md5
     * @return boolean
     */
    public static boolean complete(String md5) {
        if (checkRecord(md5)) {
            for (boolean b : CHUNK_RECORD_MAP.get(md5).complete) {
                if (!b) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否记录
     *
     * @param md5 文件md5
     * @return boolean
     */
    public static boolean checkRecord(String md5) {
        return CHUNK_RECORD_MAP.containsKey(md5);
    }

    /**
     * 为文件添加上传分块记录
     *
     * @param md5 文件md5
     * @param chunkIndex 块索引
     */
    public static void add(String md5, int chunkIndex) {
        CHUNK_RECORD_MAP.get(md5).complete[chunkIndex] = true;
    }

    /**
     * 从map中删除键为文件md5的键值对
     *
     * @param md5 文件md5
     */
    public static void remove(String md5) {
        if (checkRecord(md5)) {
            CHUNK_RECORD_MAP.remove(md5);
        }
    }

    /**
     * 设置文件记录
     *
     * @param md5  文件md5
     * @param chunkTotal 块总数
     */
    public static void addFileRecord(String md5, int chunkTotal) {
        CHUNK_RECORD_MAP.put(md5, new Record(chunkTotal));
    }


    /**
     * 内部类记录分块上传文件信息
     */
    private static class Record {
        // 块记录值
        boolean[] complete;

        Record(int n) {
            this.complete = new boolean[n];
        }
    }
}
