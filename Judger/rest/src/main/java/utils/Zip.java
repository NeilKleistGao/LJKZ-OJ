package utils;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.*;

public class Zip {
    public static void unzip(String filePath, String pid) {
        //targetPath输出文件路径
        File targetFile = new File("/home/neilkleistgao/ljkz_data");
        // 如果目录不存在，则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //sourcePath压缩包文件路径
        try (ZipFile zipFile = new ZipFile(new File(filePath))) {
//            System.out.println("file nums:" + zipFile.size());
            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                //依次获取压缩包内的文件实体对象
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
//                System.out.println("this file size:" + entry.getSize());
                String name = entry.getName();
                if (entry.isDirectory()) {
                    continue;
                }
                try (BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry))) {
                    // 需要判断文件所在的目录是否存在，处理压缩包里面有文件夹的情况
                    String outName = "/home/neilkleistgao/ljkz_data/" + pid + "/" + name;
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
        finally {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
