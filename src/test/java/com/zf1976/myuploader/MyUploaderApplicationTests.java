package com.zf1976.myuploader;

import com.zf1976.uploader.UploaderApplication;
import com.zf1976.uploader.dao.FileDao;
import com.zf1976.uploader.model.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UploaderApplication.class)
public class MyUploaderApplicationTests {

    @Autowired
    private FileDao dao;

    @Test
    public void contextLoads() {
        String filename = "hanbi.jpg";
        System.out.println(filename.substring(filename.indexOf(".")));
    }

}
