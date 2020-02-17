package com.cvicse.shopkill.controller;

import com.cvicse.shopkill.domain.MiaoshaOrder;
import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.domain.OrderInfo;
import com.cvicse.shopkill.redis.RedisService;
import com.cvicse.shopkill.result.CodeMsg;
import com.cvicse.shopkill.service.GoodsService;
import com.cvicse.shopkill.service.MiaoshaService;
import com.cvicse.shopkill.service.MiaoshaUserService;
import com.cvicse.shopkill.service.OrderService;
import com.cvicse.shopkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){

        model.addAttribute("user", user);
        if (user==null){
            return "to_login";
        }

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock<=0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
        }

        //判断是否秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order!=null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);

        return "order_detail";
    }

}
