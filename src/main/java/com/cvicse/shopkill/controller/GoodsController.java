package com.cvicse.shopkill.controller;

import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.redis.GoodsKey;
import com.cvicse.shopkill.redis.RedisService;
import com.cvicse.shopkill.service.GoodsService;
import com.cvicse.shopkill.service.MiaoshaUserService;
import com.cvicse.shopkill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author luozhe73
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest httpServletRequest, HttpServletResponse response, Model model, MiaoshaUser user) {

        model.addAttribute("user", user);
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        //return "goods_list";
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        WebContext ctx = new WebContext(httpServletRequest, response,
                httpServletRequest.getServletContext(), httpServletRequest.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {

        //snowFlake
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;


        if (now < startAt) {
            //秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);

        } else if (now > endAt) {
            //秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);

        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
