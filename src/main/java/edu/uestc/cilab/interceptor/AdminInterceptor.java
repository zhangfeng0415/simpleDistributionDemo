package edu.uestc.cilab.interceptor;

import edu.uestc.cilab.constant.SessionConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Copyright ©2017 张书洲(https://github.com/shuzhou)@电子科技大学计算机科学与工程学院
 * <p/>
 * Create with education-demo in edu.uestc.cilab.interceptor
 * Class: AdminInterceptor.java
 * User: joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2017-05-04 09:09
 * Description: 对/admin/**的访问进行登录及权限等的校验
 */
public class AdminInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //获取请求的URL
        String url = httpServletRequest.getRequestURI();
        //URL:/admin/login是公开的;这个demo是除了/admin/login是不需要权限校验的，其它的URL都进行拦截控制
        if (url.indexOf("login") >= 0) {
            return true;
        }
        //获取Session
        HttpSession session = httpServletRequest.getSession();
        String username = (String) session.getAttribute(SessionConstant.USER_NUMBER);

        if (username != null) {
            return true;
        }

        //不符合条件的，跳转到登录界面
        httpServletRequest.getRequestDispatcher("/index.html?forward=" + url).forward(httpServletRequest, httpServletResponse);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
