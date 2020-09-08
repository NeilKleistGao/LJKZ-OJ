package com.pro.wlm.utils;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.*;

public class Zip {
//    public static void unzip(String filename) {
//        ZipFile file = null;
//        try {
//            file = new ZipFile("d:/temp/" + filename);
//            Enumeration entries = file.entries();
//
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = (ZipEntry)entries.nextElement();
//                File target = new File("d:/temp/" + filename + "_dir", entry.getName());
//                if (!target.getParentFile().exists()) {
//                    target.getParentFile().mkdir();
//                }
//
//                target.createNewFile();
//                InputStream inputStream = file.getInputStream(entry);
//                FileOutputStream outputStream = new FileOutputStream(target);
//
//                byte[] bytes = new byte[1024];
//                int len = 0;
//                while ((len = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, len);
//                }
//
//                outputStream.close();
//                inputStream.close();
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (file != null) {
//                    file.close();
//                }
//
//                File removedFile = new File("d:/temp/", filename);
//                removedFile.delete();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }



    public static void unzip(String filePath) {
        //targetPath输出文件路径
        File targetFile = new File("d:/temp/");
        // 如果目录不存在，则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //sourcePath压缩包文件路径
        try (ZipFile zipFile = new ZipFile(new File(filePath))) {
            System.out.println("file nums:" + zipFile.size());
            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                //依次获取压缩包内的文件实体对象
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                System.out.println("this file size:" + entry.getSize());
                String name = entry.getName();
                if (entry.isDirectory()) {
                    continue;
                }
                try (BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry))) {
                    // 需要判断文件所在的目录是否存在，处理压缩包里面有文件夹的情况
                    String outName = "d:/temp/" + "/" + name;
                    File outFile = new File(outName);
                    File tempFile = new File(outName.substring(0, outName.lastIndexOf("/")));
                    if (!tempFile.exists()) {
                        tempFile.mkdirs();
                    }
                    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile))) {
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
