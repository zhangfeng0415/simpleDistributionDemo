
package edu.uestc.cilab.exception;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng0415)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with apms in com.iustu.util.exception
 * Class: ExistException.java
 * @author : zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:已经存在异常，表示该数据在系统中已经存在
 */
public class ExistException extends Exception{

    public ExistException(String msg) {
        super(msg);
    }

    public ExistException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
