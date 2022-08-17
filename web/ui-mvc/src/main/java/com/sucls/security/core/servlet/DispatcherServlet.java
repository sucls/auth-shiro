package com.sucls.security.core.servlet;

import com.sucls.security.core.handle.AnnotationRequestInfoParser;
import com.sucls.security.core.handle.DefaultServletRequestHandler;
import com.sucls.security.core.handle.ServletRequestHandler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sucl
 * @date 2022/7/6 20:39
 * @since 1.0.0
 */
public class DispatcherServlet extends HttpServlet {

    private ServletRequestHandler requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        AnnotationRequestInfoParser requestInfoParser = new AnnotationRequestInfoParser();
        requestHandler = new DefaultServletRequestHandler(requestInfoParser.parseAnnotation());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        if(StringUtils.isEmpty(requestURI)){
            requestURI = "/";
        }
        if(requestURI.endsWith("/")){
            requestURI += "index.html";
        }

        requestHandler.handle(requestURI,req,resp);
    }


}
