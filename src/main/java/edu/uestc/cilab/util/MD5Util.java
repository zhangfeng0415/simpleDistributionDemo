package edu.uestc.cilab.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright ©2016 创联工作室@电子科技大学计算机科学与工程学院 iustu.com(http://www.iustu.com)
 * <p>
 * Create with marxism in com.iustu.marxism.util
 * Class: MD5Util.java
 * User: joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2016-09-18 14:14
 * Description:
 */
public class MD5Util {
    /**
     * 计算输入字符串的MD5 哈希值
     *
     * @param input
     * @return MD5哈希值
     */
    public static String MD5Value(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
