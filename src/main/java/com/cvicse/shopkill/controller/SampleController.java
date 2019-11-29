package com.cvicse.shopkill.controller;

import com.cvicse.shopkill.domain.User;
import com.cvicse.shopkill.redis.RedisService;
import com.cvicse.shopkill.redis.UserKey;
import com.cvicse.shopkill.result.Result;
import com.cvicse.shopkill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){

        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Long> redisGet(){

        Long v1 = redisService.get(UserKey.getById,"key1",Long.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){

        boolean flag = redisService.set(UserKey.getById,"key1",123L);
        return Result.success(flag);
    }
}
