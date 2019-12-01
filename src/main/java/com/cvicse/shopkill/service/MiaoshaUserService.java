package com.cvicse.shopkill.service;

import com.cvicse.shopkill.dao.MiaoshaUerDao;
import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.exception.GlobalException;
import com.cvicse.shopkill.redis.MiaoshaUserKey;
import com.cvicse.shopkill.redis.RedisService;
import com.cvicse.shopkill.result.CodeMsg;
import com.cvicse.shopkill.util.MD5Util;
import com.cvicse.shopkill.util.UUIDUtil;
import com.cvicse.shopkill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @author luozhe73
 */
@Service
public class MiaoshaUserService implements MiaoshaUerDao {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUerDao miaoshaUerDao;

    @Autowired
    RedisService redisService;

    @Override
    public MiaoshaUser getById(long id) {
        return miaoshaUerDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String formPass = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        MiaoshaUser user = getById(Long.parseLong(mobile));

        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String slatdb = user.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, slatdb);
        if (!clacPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        addCookie(response, user);

        return true;

    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user) {
        //生成cookie
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        }

        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, user);
        }

        return user;
    }
}
