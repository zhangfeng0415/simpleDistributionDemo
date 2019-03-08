package edu.uestc.cilab.util;

/**
 * Copyright ©2017 张书洲(https://github.com/shuzhou)@电子科技大学计算机科学与工程学院
 * <p/>
 * Create with education in edu.uestc.cilab.util
 * Class: DBUtil.java
 * User: joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2017-06-20 14:14
 * Description:
 */
public class DBUtil {

    /**
     * 将对象属性名改成数据库字段名(所有映射规则均为去掉下划线且下划线后的第一个首字母大写)
     *
     * @param propertyName 属性名
     * @return
     */
    public static String getColumNameByProperty(String propertyName) {
        StringBuffer stringBuffer = new StringBuffer();

        for (byte b : propertyName.getBytes()) {
            if (b <= 'Z' && b >= 'A') {
                stringBuffer.append("_" + (char) (b + 32));
            } else {
                stringBuffer.append((char) b);
            }
        }

        return stringBuffer.toString();
    }
}
