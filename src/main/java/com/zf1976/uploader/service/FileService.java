package com.zf1976.uploader.service;

import com.zf1976.uploader.dao.FileDao;
import com.zf1976.uploader.model.ChunkFile;
import com.zf1976.uploader.model.File;
import com.zf1976.uploader.utils.FileUtil;
import com.zf1976.uploader.utils.ChunkRecordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

/**
 * 文件上传服务
 * @author mac
 */
@Service
public class FileService {

    private final FileDao fileDao;

    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
        initUploadDirectory();
    }

    /**
     * 初始化上传目录
     */
    private void initUploadDirectory(){
        try {
            Path path = Paths.get(FileUtil.getUploadPath());
            java.io.File rootDirectory;
            if (!path.toFile().exists()) {
                 rootDirectory = Files.createDirectory(path).toFile();
                if (!rootDirectory.exists()) {
                    throw new RuntimeException("root directory cannot been null");
                }
            }
        } catch (IOException ignored) {
            // 初始化失败，停止程序
            System.exit(-1);
        }
    }

    /**
     * 上传文件 md5 校验防止重复上传文件
     *
     * @param md5 文件md5
     * @param file 上传文件
     */
    @Transactional(rollbackFor = Exception.class)
    public Optional<Void> uploadFile(String filename, String md5, MultipartFile file) throws IOException {
        // 生成文件md5防止重复上传
        String md5Filename = ObjectUtils.isEmpty(md5) ? DigestUtils.md5DigestAsHex(file.getInputStream()) : md5;
        // 根据文件md5校验文件是否已上传
        if (!checkMd5(md5Filename)) {
            throw new RuntimeException("file:" + filename + " is exists");
        } else {
            // 文件md5作为文件名
            FileUtil.write(md5Filename, file.getInputStream());
            final File buildFile = new File();
            buildFile.setMd5(md5)
                     .setName(filename)
                     .setSize(formatByte(file.getSize()))
                     .setUploadTime(new Date());
            fileDao.save(buildFile);
        }
        return Optional.empty();
    }

    /**
     * 多文件上传
     *
     * @date 2021-03-30 20:35:18
     * @param fileList 文件数组
     * @return {@link Optional<Void>}
     */
    @Transactional(rollbackFor = Exception.class)
    public Optional<Void> uploadFileList(MultipartFile[] fileList) {
        for (MultipartFile file : fileList) {
            // 获取文件名
            String filename = file.getOriginalFilename();
            try (InputStream inputStream = file.getInputStream()) {
                // 文件进行md5记录
                final String md5DigestAsHex = DigestUtils.md5DigestAsHex(inputStream);
                //上传写入文件
                return this.uploadFile(filename, md5DigestAsHex, file);
            } catch (IOException ioException) {
                throw new RuntimeException();
            }
        }
        return Optional.empty();
    }

    /**
     * 分块上传文件
     * @param md5 文件md5
     * @param fileSize 文件大小
     * @param chunkTotal 块总数
     * @param chunkIndex 分块号
     * @param file 文件
     * @throws IOException exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Optional<Void> uploadWithChunk(String filename, String md5, Long fileSize, Integer chunkTotal, Integer chunkIndex, MultipartFile file) throws IOException {
        // 文件小于限制直接上传
        if (ObjectUtils.isEmpty(chunkTotal)) {
            uploadFile(filename, md5, file);
        }
        // 设置文件分块记录
        if (!ChunkRecordUtil.checkRecord(md5)) {
            ChunkRecordUtil.addFileRecord(md5, chunkTotal);
        }
        final ChunkFile chunkFile = ChunkFile.ChunkFileBuilder.builder()
                                                          .fileSize(fileSize)
                                                          .chunkSize(file.getSize())
                                                          .chunkIndex(chunkIndex)
                                                          .chunkTotal(chunkTotal)
                                                          .build();
        // 写入文件块, 文件md5作为写入文件名
        if (FileUtil.writeWithChunk(md5, chunkFile, file.getInputStream())) {
            // 记录文件块
            ChunkRecordUtil.add(md5, chunkIndex);
        }
        // 判断文件是否上传完毕
        if (ChunkRecordUtil.complete(md5)) {
            // 上传完成，删除文件记录
            ChunkRecordUtil.remove(md5);
            final File buildFile = new File();
            buildFile.setMd5(md5)
                     .setName(filename)
                     .setSize(formatByte(file.getSize()))
                     .setUploadTime(new Date());
            fileDao.save(buildFile);
        }
        return Optional.empty();
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5 文件md5
     * @return boolean
     */
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return fileDao.getByFile(file) == null;
    }

    /**
     * 格式化
     *
     * @param size 文件大小
     * @return
     */
    @SuppressWarnings("all")
    public static String formatByte(long size) {
        double doubleSize = (double) size;
        final StringBuilder stringBuilder = new StringBuilder();
        if (doubleSize < 1024) {
            stringBuilder.append(String.format("%.2f", doubleSize));
            stringBuilder.append("B");
        } else if (doubleSize < 1024 * 1024) {
            stringBuilder.append(String.format("%.2f", doubleSize / 1024));
            stringBuilder.append("KB");
        } else {
            stringBuilder.append(String.format("%.2f", doubleSize / 1024 / 1024));
            stringBuilder.append("MB");
        }
        return stringBuilder.toString();
    }

}
