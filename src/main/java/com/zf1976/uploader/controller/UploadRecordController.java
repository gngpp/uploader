package com.zf1976.uploader.controller;

import com.zf1976.uploader.dao.PageInfo;
import com.zf1976.uploader.model.File;
import com.zf1976.uploader.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mac
 */
@RestController
@RequestMapping("/record")
public class UploadRecordController {

    private final FileService service;

    public UploadRecordController(FileService service) {
        this.service = service;
    }

    @PostMapping("/page")
    public ResponseEntity<PageInfo<File>> selectFileRecords(@RequestParam Integer pageCount, @RequestParam Integer pageSize)  {
        return ResponseEntity.ok(service.selectFilePage(pageCount, pageSize));
    }
}
