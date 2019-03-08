
package edu.uestc.cilab.exception;


/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with apms in com.iustu.util.exception
 * Class: ParamException.java
 * User: zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:参数异常
 */
public class ParamException extends Exception{

    public ParamException(String msg) {
        super(msg);
    }

    public ParamException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
