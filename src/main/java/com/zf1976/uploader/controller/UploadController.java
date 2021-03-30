package com.zf1976.uploader.controller;

import com.zf1976.uploader.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ant
 * Create by Ant on 2021/3/30 11:18 AM
 */
@RestController
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/multi_file")
    public void multiFileUpload(MultipartFile[] fileArray) {

    }

    @PostMapping("/File")
    public void upload(String name, String md5, MultipartFile file) throws IOException {
        fileService.upload(name, md5,file);
    }

    @PostMapping("/BigFile")
    public void upload(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file) throws IOException {
        fileService.uploadWithChunk(name, md5,size,chunks,chunk,file);
    }

    @GetMapping("/QuickUpload")
    public boolean upload(String md5) {
        return fileService.checkMd5(md5);
    }
}
