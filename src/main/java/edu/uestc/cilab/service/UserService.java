package edu.uestc.cilab.service;

import edu.uestc.cilab.entity.User;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.exception.ParamException;
import edu.uestc.cilab.util.PageUtil;

import java.util.List;
/**
 * @author: zhangfeng
 * @date: 2019-04-11 13:48
 * @description: 用户接口
 **/
public interface UserService {

    /**
     * 添加用户
     *
     * @param user 用户
     * @author zhangfeng 作者
     */
    void add(User user) throws ExistException;

    /**
     * 删除用户
     *
     * @param id 用户id
     * @author zhangfeng 作者
     */
    void delete(Integer id);
    /**
     * 更新用户
     * @param user 用户
     * @author zhangfeng 作者
     */
    void update(User user);

    /**
     * 停用用户帐号
     *
     * @param id 用户ID
     * @author zhangfeng 作者
     */
    void stop(Integer id);

    /**
     * 启用用户帐号
     *
     * @param id 用户ID
     * @author zhangfeng 作者
     */
    void start(Integer id);
    /**
     * 更新密码
     *
     * @param id          教师ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @author zhangfeng 作者
     */
    void updatePassword(Integer id, String oldPassword, String newPassword) throws ParamException;

    /**
     * 根据工号查询用户的信息
     * @param number  用户号码
     * @return
     */
    List<User> selectUser(String number);

    /**
     * 获取所有角色信息
     *
     * @author zhangfeng
     */
    List<User> selectAllUser();
}
