package com.cvicse.shopkill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luozhe73
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern =Pattern.compile("1\\d{10}");

    public  static boolean isMobile(String str){
        if (StringUtils.isEmpty(str)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(str);
        return m.matches();
    }
}
