package com.sucls.security.core;

/**
 * @author sucl
 * @date 2022/6/28 16:45
 * @since 1.0.0
 */
public class ResponseResult {

    private String code = "0000";
    private Object result;

    public static ResponseResult ok(Object result){
        ResponseResult responseResult = new ResponseResult();
        responseResult.result = result;
        return responseResult;
    }

    public static ResponseResult error(String... messages){
        ResponseResult responseResult = new ResponseResult();
        if(messages.length > 0){
            if(messages.length>1){
                responseResult.result = messages[1];
                responseResult.code = messages[0];
            }else{
                responseResult.result = messages[0];
            }
        }
        return responseResult;
    }

}
