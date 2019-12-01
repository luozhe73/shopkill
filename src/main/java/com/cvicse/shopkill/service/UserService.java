package com.cvicse.shopkill.service;

import com.cvicse.shopkill.dao.UserDao;
import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luozhe73
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

}
