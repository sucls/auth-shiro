package com.sucls.security.api;

import com.sucls.security.core.Path;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.Map;
import java.util.Properties;

/**
 * @author sucl
 * @date 2022/7/7 15:34
 * @since 1.0.0
 */
@Path("/system")
public class SystemServlet {

    @RequiresRoles("ROLE_ADMIN")
    @Path("/getProperties.json")
    public Properties getProperties(){
        return System.getProperties();
    }

    @RequiresRoles("ROLE_ADMIN")
    @Path("/getEnv.json")
    public Map getEnv(){
        return System.getenv();
    }

}
