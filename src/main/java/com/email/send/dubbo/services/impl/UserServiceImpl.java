package com.email.send.dubbo.services.impl;

import com.email.send.dao.UserDao;
import com.email.send.dto.User;
import com.email.send.dubbo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: WUQW
 * @Date: 2018/11/30 10:06
 * @Description:
 */
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(User user) {
        boolean verfiy = verfiy(user);
        if (verfiy) {
            logger.debug("参数缺失");
        } else {
            try {
                userDao.insertUser(user);
            } catch (Exception e) {
                logger.error("用户新增失败，数据库操作错误");
                e.printStackTrace();
            }
        }
    }

    /*参数校验*/
    private boolean verfiy(User user) {
        if (user.getUsername() == null) {
            return false;
        }
        if (user.getPassword() == null) {
            return false;
        }
        if (user.getEmail() == null) {
            return false;
        }
        return true;
    }
}
