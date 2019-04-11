package edu.uestc.cilab.util;

/**
 * Copyright ©2016 创联工作室@电子科技大学计算机科学与工程学院 iustu.com(http://www.iustu.com)
 * <p>
 * Create with marxism in com.iustu.marxism.util
 * Class: ResultUtil.java
 * @author : joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2016-08-12 20:20
 * Description:
 */
public class ResultUtil<T> {
    private int code;
    private String message;
    private T body;

    public ResultUtil() {
        super();
    }

    public ResultUtil(int code, String message, T body) {
        super();
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public ResultUtil(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultUtil{" +
                "body=" + body +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
