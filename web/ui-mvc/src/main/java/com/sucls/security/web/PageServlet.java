package com.sucls.security.web;

import com.sucls.security.core.Path;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sucl
 * @date 2022/7/7 15:34
 * @since 1.0.0
 */
@Path("/")
public class PageServlet {

    @Path("/login.html")
    public String login(){
        return "login";
    }

    @Path("/index.html")
    public String index(){
        return "index";
    }

    @Path("/logout")
    public String logout(HttpServletRequest request){
        SecurityUtils.getSubject().logout();
        return "login";
    }

}
