package com.cvicse.shopkill.redis;

import lombok.Getter;

/**
 * @author luozhe73
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

}
