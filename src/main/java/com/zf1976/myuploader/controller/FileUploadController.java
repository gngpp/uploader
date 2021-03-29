package com.zf1976.myuploader.controller;

import com.zf1976.myuploader.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传
 * @author mac
 */
@RestController
@RequestMapping("/File")
@CrossOrigin
public class FileUploadController {

    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/")
    public void upload(String name, String md5, MultipartFile file) throws IOException {
        fileService.upload(name, md5,file);
    }
}
