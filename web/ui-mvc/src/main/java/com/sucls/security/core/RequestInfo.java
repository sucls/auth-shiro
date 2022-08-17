package com.sucls.security.core;

import java.lang.reflect.Method;

/**
 * @author sucl
 * @date 2022/6/28 15:08
 * @since 1.0.0
 */
public class RequestInfo {

    private Object target;

    private Method method;

    private Class[] params;

    public RequestInfo(Object target, Method method, Class[] params) {
        this.target = target;
        this.method = method;
        this.params = params;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class[] getParams() {
        return params;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }
}
