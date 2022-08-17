package com.sucls.security.web;

import com.sucls.security.core.Path;

/**
 * @author sucl
 * @date 2022/7/7 15:34
 * @since 1.0.0
 */
@Path("/admin")
public class AdminPageServlet {

    @Path("/index.html")
    public String index(){
        return "admin/index";
    }

}
