package com.dl.mao.util;

/**
 * @author xingguanghui
 * @create 2017-11-27 11:48
 **/
public class StringUtils {
    public static Boolean isEmptyOrNull(String str){
        if(str==null){
            return true;
        }else if("".equals(str.trim())){
            return true;
        }else {
            return false;
        }
    }
}
