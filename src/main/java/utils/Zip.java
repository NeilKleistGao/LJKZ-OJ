package utils;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.*;

public class Zip {
    public static void unzip(String filename) {
        ZipFile file = null;
        try {
            file = new ZipFile("../data/" + filename);
            Enumeration entries = file.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                File target = new File("../data/" + filename + "_dir", entry.getName());
                if (!target.getParentFile().exists()) {
                    target.getParentFile().mkdir();
                }

                target.createNewFile();
                InputStream inputStream = file.getInputStream(entry);
                FileOutputStream outputStream = new FileOutputStream(target);

                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }

                outputStream.close();
                inputStream.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (file != null) {
                    file.close();
                }

                File removedFile = new File("../data", filename);
                removedFile.delete();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
