package com.zf1976.uploader.controller;

import com.zf1976.uploader.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒传
 * @author mac
 */
@RestController
@RequestMapping("/QuickUpload")
@CrossOrigin
public class QuickUploadController {

    private final FileService fileService;

    public QuickUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public boolean upload(String md5) {
        return fileService.checkMd5(md5);
    }
}
