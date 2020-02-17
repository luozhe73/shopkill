package com.cvicse.shopkill.service;


import com.cvicse.shopkill.dao.GoodsDao;
import com.cvicse.shopkill.domain.MiaoshaGoods;
import com.cvicse.shopkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId){
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goods) {

        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getGoodsId());
        goodsDao.reduceStock(goods);
    }
}
