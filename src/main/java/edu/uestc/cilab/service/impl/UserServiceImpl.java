package edu.uestc.cilab.service.impl;

import com.github.pagehelper.PageHelper;
import edu.uestc.cilab.constant.PasswordConstant;
import edu.uestc.cilab.entity.User;
import edu.uestc.cilab.entity.UserExample;
import edu.uestc.cilab.exception.ExistException;
import edu.uestc.cilab.exception.ParamException;
import edu.uestc.cilab.repository.UserMapper;
import edu.uestc.cilab.service.UserService;
import edu.uestc.cilab.util.MD5Util;
import edu.uestc.cilab.util.PageUtil;
import edu.uestc.cilab.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangfeng on 2018/1/11.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void add(User user) throws ExistException {
        UserExample example =new UserExample();
        example.or().andNameEqualTo(user.getNumber());
        if(0 != userMapper.countByExample(example)){
            throw new ExistException("用戶账号重复");
        }
        String salt = RandomUtil.getStringRandom(5);
        user.setSalt(salt);
        user.setPassword(MD5Util.MD5Value(MD5Util.MD5Value(user.getPassword()) + salt));
        userMapper.insertSelective(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {

        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void stop(Integer id) {
        User user = new User();
        user.setId(id);
        user.setEnable(false);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void start(Integer id) {
        User user = new User();
        user.setId(id);
        user.setEnable(true);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updatePassword(Integer id, String oldPassword, String newPassword) throws ParamException {
        User user = userMapper.selectByPrimaryKey(id);
        if (!user.getPassword().equals(MD5Util.MD5Value(MD5Util.MD5Value(oldPassword) + user.getSalt()))) {
            throw new ParamException("旧密码输入错误");
        }
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setSalt(RandomUtil.getStringRandom(PasswordConstant.SALT_LENGTH));
        updateUser.setPassword(MD5Util.MD5Value(MD5Util.MD5Value(newPassword) + updateUser.getSalt()));
        userMapper.updateByPrimaryKeySelective(updateUser);
    }

    @Override
    public List<User> selectUser(String number) {
        UserExample userExample =new UserExample();
        userExample.or().andNumberEqualTo(number);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> users = userMapper.getAllUser();
        return users;
    }

}
