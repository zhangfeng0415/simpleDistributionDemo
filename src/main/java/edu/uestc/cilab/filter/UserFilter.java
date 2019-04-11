package edu.uestc.cilab.filter;


import edu.uestc.cilab.constant.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 用户权限过滤器
 **/
public class UserFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(UserFilter.class);

    private String redirectURL = null;
    private List<String> ignoreURLs = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectURL = filterConfig.getInitParameter("redirectURL");
        String ignoreURLStrings = filterConfig.getInitParameter("ignoreURLs");
        System.out.println(redirectURL);
        System.out.println(ignoreURLStrings);
        for (String url : ignoreURLStrings.split(",")) {
            ignoreURLs.add(url);
        }
        System.out.println(redirectURL);
        System.out.println(ignoreURLs);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (logger.isDebugEnabled()) {
            logger.debug(request.getRequestURI() + " come in parent filter");
        }

        /**
         * 不需过滤的请求直接放行
         */
        String requestURI = request.getRequestURI();
        for (String url : ignoreURLs) {
            if (requestURI.indexOf(url) != -1) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }


        /**
         * 如果用户未登录，记录目前访问的页面，并跳转到登录页面
         */
        if (null == session || null == session.getAttribute(SessionConstant.USER_ID) || "".equals(session.getAttribute(SessionConstant.USER_ID))) {
            if (requestURI.contains(".jsp")) {
                session.setAttribute(SessionConstant.REQUEST_URI, requestURI);
            }
            //请求重定向，转到登录页面
            response.sendRedirect(request.getContextPath() + "/" + redirectURL);
            return;
        }

        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        //this.ignoreURLs.clear();
    }
}
