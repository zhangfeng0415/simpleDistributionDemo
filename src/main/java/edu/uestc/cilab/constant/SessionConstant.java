package edu.uestc.cilab.constant;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng0415)@电子科技大学计算机科学与工程学院
 * <p/>
 * Create with education-demo in edu.uestc.cilab.config
 * Class: SessionConstant.java
 * @author : zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:session中存储的基本信息
 */

public class SessionConstant {

    //登录过期时,重新登录后跳转到原来访问的页面
    public static final String REQUEST_URI = "REQUEST_URI";

    //用户类型,区分管理员和用户
    public static final String USER_TYPE = "USER_TYPE";
    //用户类型:用户
    public static final String USER_TYPE_USER = "USER";
    //用户类型：管理员
    public static final String USER_TYPE_ADMIN="ADMIN";


    //用户ID
    public static final String USER_ID = "USER_ID";
    //管理员ID
    public static final String ADMIN_ID = "ADMIN_ID";

    //登录的用户登录号
    public static final String USER_NUMBER = "USER_NUMBER";
    //登录的管理员账号
    public static final String ADMIN_NUMBER = "ADMIN_NUMBER";


    //登录用户姓名
    public static final String USER_NAME = "USER_NAME";
    //登录的管理员姓名
    public static final String ADMIN_NAME = "ADMIN_NAME";


}
