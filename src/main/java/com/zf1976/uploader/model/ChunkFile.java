package com.zf1976.uploader.model;

/**
 * 块文件
 * @author mac
 */
public class ChunkFile {
    /**
     * 文件大小
     */
    Long fileSize;
    /**
     * 块大小
     */
    Long chunkSize;
    /**
     * 块总数
     */
    Integer chunkTotal;
    /**
     * 块索引
     */
    Integer chunkIndex;

    public Long getFileSize() {
        return fileSize;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public Integer getChunkTotal() {
        return chunkTotal;
    }

    public Integer getChunkIndex() {
        return chunkIndex;
    }

    @Override
    public String toString() {
        return "ChunkFile{" +
                "fileSize=" + fileSize +
                ", chunkSize=" + chunkSize +
                ", chunkTotal=" + chunkTotal +
                ", chunkIndex=" + chunkIndex +
                '}';
    }

    public static final class ChunkFileBuilder {
        Long fileSize;
        Long chunkSize;
        Integer chunkTotal;
        Integer chunkIndex;

        private ChunkFileBuilder() {
        }

        public static ChunkFileBuilder builder() {
            return new ChunkFileBuilder();
        }

        public ChunkFileBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public ChunkFileBuilder chunkSize(Long chunkSize) {
            this.chunkSize = chunkSize;
            return this;
        }

        public ChunkFileBuilder chunkTotal(Integer chunkTotal) {
            this.chunkTotal = chunkTotal;
            return this;
        }

        public ChunkFileBuilder chunkIndex(Integer chunkIndex) {
            this.chunkIndex = chunkIndex;
            return this;
        }

        public ChunkFile build() {
            ChunkFile chunkFile = new ChunkFile();
            chunkFile.chunkSize = this.chunkSize;
            chunkFile.fileSize = this.fileSize;
            chunkFile.chunkIndex = this.chunkIndex;
            chunkFile.chunkTotal = this.chunkTotal;
            return chunkFile;
        }
    }
}
