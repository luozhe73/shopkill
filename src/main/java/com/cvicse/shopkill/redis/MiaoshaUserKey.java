package com.cvicse.shopkill.redis;

public class MiaoshaUserKey extends BasePrefix {

    public static final int TOOKEN_TIME = 3600 * 24 * 2;

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOOKEN_TIME, "tk");
}
