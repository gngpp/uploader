package com.zf1976.uploader.controller;

import com.zf1976.uploader.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 大文件上传
 * @author mac
 */
@RestController
@RequestMapping("/BigFile")
@CrossOrigin
public class BigFileUploadController {

    private final FileService fileService;

    public BigFileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public void upload(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file) throws IOException {
        fileService.uploadWithChunk(name, md5,size,chunks,chunk,file);
    }
}
