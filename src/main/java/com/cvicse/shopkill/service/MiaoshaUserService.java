package com.cvicse.shopkill.service;

import com.cvicse.shopkill.dao.MiaoshaUerDao;
import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.result.CodeMsg;
import com.cvicse.shopkill.util.MD5Util;
import com.cvicse.shopkill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luozhe73
 */
@Service
public class MiaoshaUserService implements MiaoshaUerDao {

    @Autowired
    MiaoshaUerDao miaoshaUerDao;

    @Override
    public MiaoshaUser getById(long id) {
        return miaoshaUerDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }

        String formPass = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        MiaoshaUser user = getById(Long.parseLong(mobile));

        if (user == null) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //验证密码
        String dbPass = user.getPassword();
        String slatDB = user.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, slatDB);
        if (!clacPass.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;

    }
}
