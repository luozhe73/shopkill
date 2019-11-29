package com.cvicse.shopkill.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeMsg {

    private int code;

    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");

    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"server error");

    //登录模块

    //商品模块
}
