package com.cvicse.shopkill.redis;


import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * @author luozhe73
 */
@AllArgsConstructor
public abstract  class BasePrefix implements KeyPrefix {

    @Setter
    private int expireSeconds;

    @Setter
    private String prefix;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
