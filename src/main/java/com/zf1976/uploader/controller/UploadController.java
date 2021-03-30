package com.zf1976.uploader.controller;

import com.zf1976.uploader.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author ant
 * Create by Ant on 2021/3/30 11:18 AM
 */
@RestController
@CrossOrigin
@RequestMapping
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/multi_file")
    public ResponseEntity<Optional<Void>> multiFileUpload(@RequestParam("fileList") MultipartFile[] fileList) {
        return ResponseEntity.ok(fileService.uploadFileList(fileList));
    }

    @PostMapping("/file")
    public ResponseEntity<Optional<Void>> upload(String name, String md5, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(name, md5, file));
    }

    @PostMapping("/big_file")
    public ResponseEntity<Optional<Void>> upload(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadWithChunk(name, md5, size, chunks, chunk, file));
    }

    @GetMapping("/check_md5")
    public boolean checkMd5(@RequestParam("md5") String md5) {
        return fileService.checkMd5(md5);
    }
}
