package com.pro.wlm.web;

import java.io.File;
import java.io.IOException;
import com.pro.wlm.utils.Zip;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.multipart.MultipartFile;

// spring boot + mybatis 扫描有问题，必须在xml 中加上包的路径？
@RestController
public class UserController {


    @PostMapping("/upload")
    public void singleFileUpload(@RequestParam("file") MultipartFile file) {
	    String fileName = file.getOriginalFilename();
        System.out.println("upload file start "+file.getOriginalFilename());
        if (file.isEmpty()) {
            System.out.println("please select a file zip upload");
        }
        //保存路径
        String filePath = "d:/temp/";
        File dest = new File(filePath+file.getOriginalFilename());
        //创建父级目录
        if (!dest.getParentFile().exists()) {
             dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();

        }
        Zip.unzip(dest.toString());
        dest.delete();
        System.out.println("upload file and unzip file");
        return ;
    }
    

    
    
}