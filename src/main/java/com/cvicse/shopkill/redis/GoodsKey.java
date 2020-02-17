package com.cvicse.shopkill.redis;

public class GoodsKey  extends BasePrefix{

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey("gl");

    public static GoodsKey getGoodsDetail = new GoodsKey("gd");
}
