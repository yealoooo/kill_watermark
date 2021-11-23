package cn.video.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESUtil {

    private static final String KEY = "1997955904286666";
//
//    public static void main(String[] args) {
//        String content = "你好啊哈哈哈哈";
//        String key = "1538663015386630";
//        String encodeStr = encryptAES(content, key);
//
//        System.out.println(encodeStr);
//
//        String s = decryptAES(encodeStr, key);
//        System.out.println(s);
//    }

    /**
     * PKCS5Padding -- Pkcs7 两种padding方法都可以
     *
     * @param content 69f23a1a98ca3d406692742ab7033f3b  16进制
     * @return masget2019
     */
    public static String decryptAES(String content) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //解密后是16进制
            return new String(cipher.doFinal(parseHexStr2Byte(content)));
        } catch (Exception e) {
            System.out.println(String.format("解密失败:，content：%s, key: %s", content, KEY));
        }
        return content;
    }



    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encryptAES(String content) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return parseByte2HexStr(result);
        } catch (Exception e) {
            System.out.println(String.format("加密失败:，content：%s, key: %s", content, KEY));
        }
        return content;
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
