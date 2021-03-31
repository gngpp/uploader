package com.zf1976.uploader.controller;

import com.zf1976.uploader.dao.PageInfo;
import com.zf1976.uploader.model.File;
import com.zf1976.uploader.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

/**
 * @author mac
 */
@RestController
@CrossOrigin
@RequestMapping("/record")
public class UploadRecordController {

    private final FileService service;

    public UploadRecordController(FileService service) {
        this.service = service;
    }

    @PostMapping("/page")
    public ResponseEntity<PageInfo<File>> selectFileRecords(@RequestParam Integer pageNumber, @RequestParam Integer pageSize)  {
        return ResponseEntity.ok(service.selectFilePage(pageNumber, pageSize));
    }

    @PostMapping("/delete")
    public ResponseEntity<Optional<Void>> deleteFile(@RequestParam Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
