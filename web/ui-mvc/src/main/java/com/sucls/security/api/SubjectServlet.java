package com.sucls.security.api;

import com.sucls.security.core.Path;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

/**
 * @author sucl
 * @date 2022/7/7 15:34
 * @since 1.0.0
 */
@Path("/subject")
public class SubjectServlet {

    @RequiresRoles("ROLE_USER")
    @Path("/getSubject.json")
    public Subject getSubject(){
        return SecurityUtils.getSubject();
    }

}
