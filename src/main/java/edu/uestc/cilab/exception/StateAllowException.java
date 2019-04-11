package edu.uestc.cilab.exception;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng04150415)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with marxism in com.iustu.marxism.util.exception
 * Class: StateAllowException.java
 * @author : zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:
 */
public class StateAllowException extends Exception {

    public StateAllowException(String msg) {
        super(msg);
    }

    public StateAllowException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
