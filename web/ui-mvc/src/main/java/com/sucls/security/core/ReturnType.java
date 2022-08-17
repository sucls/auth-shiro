package com.sucls.security.core;

/**
 * @author sucl
 * @date 2022/7/7 15:25
 * @since 1.0.0
 */
public enum ReturnType {

    html,

    json;

    public static ReturnType of(String type){
        return ReturnType.valueOf(type);
    }
}
