package com.zf1976.myuploader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author mac
 */
@Configuration
public class UploadConfig {

    public static String catalog;

    @Value("${upload.catalog}")
    public void setPath(String path) {
        UploadConfig.catalog = path;
    }
}
