package edu.uestc.cilab.controller;

import edu.uestc.cilab.constant.ResponseConstant;
import edu.uestc.cilab.constant.SessionConstant;
import edu.uestc.cilab.entity.User;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.exception.ParamException;
import edu.uestc.cilab.service.UserService;
import edu.uestc.cilab.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangfeng on 2018/1/11.
 */
@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加用户")
    public ResultUtil add(@Valid @ModelAttribute User user, BindingResult result){
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }

        try {
            userService.add(user);
        } catch (ExistException e) {
            logger.info("用户({})添加用户({})失败:{}", session.getAttribute(SessionConstant.USER_NUMBER), user);
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE, e.getMessage());
        }

        if (logger.isInfoEnabled()){
            logger.info("用户("+session.getAttribute(SessionConstant.USER_NAME)+ ")添加用户:" + user);
        }

        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "添加成功!", null);
    }

    @RequestMapping(value = "/admin/user/update",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("修改用户(仅限修改账号和名字)")
    public ResultUtil update(@Valid @ModelAttribute User user,BindingResult result)
    {
        if (result.hasErrors()) {
            return new ResultUtil<String>(ResponseConstant.ResponseCode.FAILURE,
                    result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                    null);
        }
        userService.update(user);
        if(logger.isInfoEnabled())
        {
            logger.info("用户（"+session.getAttribute(SessionConstant.USER_NAME)+"）修改了用户:" + user);
        }
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS,"更新成功",null);
    }

    @RequestMapping(value = "/admin/user/stop/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("用户账号停用")
    public ResultUtil stop(@ApiParam(value = "用户ID",required = true) @PathVariable Integer id){

        userService.stop(id);
        if (logger.isInfoEnabled()) {
            logger.info("用户(" + session.getAttribute(SessionConstant.USER_NAME) + ")停用用户账号:" + id);
        }
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "停用成功!", null);
    }

    @RequestMapping(value = "/admin/user/start/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("用户账号启用")
    public ResultUtil start(@ApiParam(value = "用户ID",required = true) @PathVariable Integer id){

        userService.start(id);
        if (logger.isInfoEnabled()) {
            logger.info("用户(" + session.getAttribute(SessionConstant.USER_NAME) + ")启用用户账号:" + id);
        }
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "启用成功!", null);
    }

    @RequestMapping(value = "/admin/user/delete/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("删除用户账号")
    public ResultUtil delete(@ApiParam(value = "用户ID",required = true) @PathVariable Integer id){
        userService.delete(id);
        if (logger.isInfoEnabled()) {
            logger.info("用户(" + session.getAttribute(SessionConstant.USER_NAME) + ")删除用户账号:" + id);
        }
        return new ResultUtil<String>(ResponseConstant.ResponseCode.SUCCESS, "删除成功!", null);
    }

    @RequestMapping(value = {"/admin/password/adminReset"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("管理员修改密码")
    public ResultUtil updateAdminPassword(@ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
                                     @ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
                                     @ApiParam(value = "重复新密码", required = true) @RequestParam String reapeatPassword) throws ParamException {

        if (!newPassword.equals(reapeatPassword)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "两次新密码输入不一致");
        }
        userService.updatePassword((Integer) session.getAttribute(SessionConstant.ADMIN_ID), oldPassword, newPassword);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "密码更新成功!");
    }
    @RequestMapping(value = {"/user/password/userReset"}, method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("用户修改密码")
    public ResultUtil updateUserPassword(@ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
                                     @ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
                                     @ApiParam(value = "重复新密码", required = true) @RequestParam String reapeatPassword) throws ParamException {

        if (!newPassword.equals(reapeatPassword)) {
            return new ResultUtil(ResponseConstant.ResponseCode.PARAM_ERROR, "两次新密码输入不一致");
        }
        userService.updatePassword((Integer) session.getAttribute(SessionConstant.USER_ID), oldPassword, newPassword);
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "密码更新成功!");
    }



    @RequestMapping(value = {"/admin/getUser"}, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取所有用户")
    public ResultUtil getAllUser(){
        List<User> users = userService.selectAllUser();
        return new ResultUtil(ResponseConstant.ResponseCode.SUCCESS, "查询成功",users);
    }
}
