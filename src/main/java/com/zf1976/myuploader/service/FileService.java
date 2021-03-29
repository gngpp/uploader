package com.zf1976.myuploader.service;

import com.zf1976.myuploader.config.UploadConfig;
import com.zf1976.myuploader.dao.FileDao;
import com.zf1976.myuploader.model.File;
import com.zf1976.myuploader.utils.FileUtils;
import com.zf1976.myuploader.utils.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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
     * 创建上传目录
     */
    private void initUploadDirectory(){
        try {
            Path path = Paths.get(FileUtils.getUploadPath());
            java.io.File rootDirectory;
            if (!path.toFile().exists()) {
                 rootDirectory = Files.createDirectory(path).toFile();
                if (!rootDirectory.exists()) {
                    throw new RuntimeException("root directory cannot been null");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件 md5 校验防止重复上传文件
     *
     * @param md5 文件md5
     * @param file 上传文件
     */
    public void upload(String name, String md5, MultipartFile file) throws IOException {
        String uploadPath = FileUtils.getUploadPath();
        String md5DigestAsHex;
        if (ObjectUtils.isEmpty(md5)) {
            md5DigestAsHex = DigestUtils.md5DigestAsHex(file.getInputStream());
        } else {
            md5DigestAsHex = md5;
        }
        if (!checkMd5(md5DigestAsHex)) {
            throw new RuntimeException("file:" + name + " is exists");
        } else {
            FileUtils.write(uploadPath, file.getOriginalFilename(), file.getInputStream());
            fileDao.save(new File(name, md5DigestAsHex, uploadPath, new Date()));
        }
    }

    /**
     * 分块上传文件
     * @param md5 文件md5
     * @param size 文件大小
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = UploadUtils.getFileName(md5, chunks);
        FileUtils.writeWithBlok(UploadConfig.catalog + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        UploadUtils.addChunk(md5,chunk);
        if (UploadUtils.isUploaded(md5)) {
            UploadUtils.removeKey(md5);
            fileDao.save(new File(name, md5,UploadConfig.catalog + fileName, new Date()));
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5
     * @return
     */
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return fileDao.getByFile(file) == null;
    }

}
