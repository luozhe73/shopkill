package com.cvicse.shopkill.controller;

import com.cvicse.shopkill.result.CodeMsg;
import com.cvicse.shopkill.result.Result;
import com.cvicse.shopkill.service.MiaoshaUserService;
import com.cvicse.shopkill.util.ValidatorUtil;
import com.cvicse.shopkill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author luozhe73
 */
@Controller
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        //登陆
        miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }
}
