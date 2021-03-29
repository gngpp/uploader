package com.zf1976.uploader.controller;

import com.zf1976.uploader.utils.LogUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zf1976.uploader.utils.LogUtils.logToFile;

/**
 * 测试日志功能
 * @author mac
 */
@RestController
@RequestMapping("/Ex")
@SuppressWarnings("all")
public class TestExceptionController {
    /**
     * 测试日志切面
     * @return
     */
    @GetMapping("/aspect")
    public int aspect() {
        int i = 1 / 0;
        return i;
    }

    /**
     * 测试日志util
     */
    @GetMapping("/util")
    public void util() {
        try {
            System.out.println(1/0);
        } catch (Exception e) {
            LogUtils.logToFile(e);
        }
    }
}
