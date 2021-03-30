package com.zf1976.uploader.controller;

import com.zf1976.uploader.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mac
 */
@RestController
@RequestMapping("/record")
public class UploadRecordController {

    public ResponseEntity<File> selectFileRecords() {
        return ResponseEntity.ok(null);
    }
}
