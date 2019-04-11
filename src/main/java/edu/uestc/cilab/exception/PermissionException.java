
package edu.uestc.cilab.exception;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng0415)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with apms in com.iustu.util.exception
 * Class: PermissionException.java
 * @author : zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:权限异常，禁止此项操作
 */

public class PermissionException extends Exception{

    public PermissionException(String msg) {
        super(msg);
    }

    public PermissionException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
