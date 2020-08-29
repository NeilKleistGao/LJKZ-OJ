package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final char[] HEX_MAP = {'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String encrypt(String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = content.getBytes();
            byte[] digest = md5.digest(bytes);

            StringBuffer buffer = new StringBuffer();
            for (byte b : digest) {
                buffer.append(HEX_MAP[(b >> 4) & 15]);
                buffer.append(HEX_MAP[b & 15]);
            }

            return buffer.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
