package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SessionConstant;
import edu.uestc.cilab.entity.User;
import edu.uestc.cilab.service.UserService;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

import static edu.uestc.cilab.util.MD5Util.MD5Value;

/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 登录
 **/
@Controller
public class LoginController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("登录")
    public ResultUtil<String> login(@ApiParam(value = "工号", required = true) @RequestParam String number,
                                    @ApiParam(value = "密码", required = true) @RequestParam String password) {

        List<User> users = userService.selectUser(number);
        if (users.size() > 0) {
            // 校验当前账号的用户是否被停用
            if (!users.get(0).getEnable()) {
                return new ResultUtil(ResponseConstant.ResponseCode.FAILURE, "当前账号被停用，请联系管理员！", 1);
            }
            String passwordSalt = MD5Value(MD5Value(password) + users.get(0).getSalt());
            if (users.get(0).getId()>=2) {
                if (passwordSalt.equals(users.get(0).getPassword())){
                    session.setAttribute(SessionConstant.USER_TYPE, SessionConstant.USER_TYPE_USER);
                    session.setAttribute(SessionConstant.USER_ID, users.get(0).getId());
                    session.setAttribute(SessionConstant.USER_NUMBER, users.get(0).getNumber());
                    session.setAttribute(SessionConstant.USER_NAME, users.get(0).getName());
                    return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "用户登录成功", "user");
                }

            } else if(passwordSalt.equals(users.get(0).getPassword()) && users.get(0).getNumber().equals(number)){
                session.setAttribute(SessionConstant.USER_TYPE, SessionConstant.USER_TYPE_ADMIN);
                session.setAttribute(SessionConstant.ADMIN_ID, users.get(0).getId());
                session.setAttribute(SessionConstant.ADMIN_NUMBER, users.get(0).getNumber());
                session.setAttribute(SessionConstant.ADMIN_NAME, users.get(0).getName());
                return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "管理员登录成功", "admin");

            }else {
                return new ResultUtil(ResponseConstant.ResponseCode.FAILURE, "账号密码错误", 1);
            }

        }
        return new ResultUtil(ResponseConstant.ResponseCode.FAILURE, "当前账号不存在", 1);
    }
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("退出登录")
    public ResultUtil logout() {
        if (session.getAttribute(SessionConstant.USER_TYPE)==SessionConstant.USER_TYPE_USER) {
            session.removeAttribute(SessionConstant.USER_ID);
            session.removeAttribute(SessionConstant.USER_NUMBER);
            session.removeAttribute(SessionConstant.USER_NAME);
            //session.invalidate();
            return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "用户退出登录成功!");
        }
        if (session.getAttribute(SessionConstant.USER_TYPE)==SessionConstant.USER_TYPE_ADMIN) {
            session.removeAttribute(SessionConstant.ADMIN_ID);
            session.removeAttribute(SessionConstant.ADMIN_NUMBER);
            session.removeAttribute(SessionConstant.ADMIN_NAME);
            //session.invalidate();
            return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "管理员退出登录成功!");
        }
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "退出登录失败!");
    }
}
