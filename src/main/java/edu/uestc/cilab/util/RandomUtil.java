package edu.uestc.cilab.util;

import java.util.Random;

/**
 * Copyright ©2016 创联工作室@电子科技大学计算机科学与工程学院 iustu.com(http://www.iustu.com)
 * <p>
 * Create with marxism in com.iustu.marxism.util
 * Class: RandomUtil.java
 * @author : joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2016-09-18 14:14
 * Description:
 */
public class RandomUtil {
    /**
     * 生成随机字符串（字母和数字组成）
     *
     * @param length 生成字符串的长度
     * @return 长度为length的随机字符串
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
