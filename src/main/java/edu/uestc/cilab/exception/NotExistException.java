package edu.uestc.cilab.exception;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with marxism in com.iustu.marxism.util.exception
 * Class: NotExistException.java
 * User: zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description: 不存在异常,请求的数据不存在
 */
public class NotExistException extends Exception {

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
