package com.cvicse.shopkill.service;

import com.cvicse.shopkill.dao.MiaoshaDao;
import com.cvicse.shopkill.domain.MiaoshaUser;
import com.cvicse.shopkill.domain.OrderInfo;
import com.cvicse.shopkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    MiaoshaDao miaoshaDao;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {

        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        return orderService.createOrder(user,goods);

    }
}
