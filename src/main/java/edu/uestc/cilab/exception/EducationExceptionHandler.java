package edu.uestc.cilab.exception;

import edu.uestc.cilab.constant.ResponseConstant;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with education in edu.uestc.cilab.exception
 * Class: EducationExceptionHandler.java
 * User: zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:
 */
public class EducationExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();

//        Map<String, Object> attributes = new HashMap<String, Object>();
//        attributes.put("code", ResponseConstant.ResponseCode.FAILURE);
//        attributes.put("message", ex.getMessage());
        view.addStaticAttribute("code", ResponseConstant.ResponseCode.FAILURE);
        view.addStaticAttribute("message", ex.getMessage());
        modelAndView.setView(view);
        return modelAndView;
    }
}
