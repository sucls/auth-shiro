package com.sucls.security.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sucl
 * @date 2022/7/6 20:48
 * @since 1.0.0
 */
public interface ServletRequestHandler {

    void handle(String requestURI, HttpServletRequest req, HttpServletResponse resp);
}
