package com.sucls.security.core.handle;

import com.google.gson.Gson;
import com.sucls.security.core.RequestInfo;
import com.sucls.security.core.ResponseResult;
import com.sucls.security.core.ReturnType;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2022/7/7 14:56
 * @since 1.0.0
 */
public class DefaultServletRequestHandler implements ServletRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServletRequestHandler.class);

    private Map<String, RequestInfo> requestInfos;

    private static String prefix = "/WEB-INF/pages/";
    private static String suffix = ".jsp";

    public DefaultServletRequestHandler(Map<String, RequestInfo> requestInfos) {
        this.requestInfos = requestInfos;
    }

    @Override
    public void handle(String requestURI, HttpServletRequest req, HttpServletResponse resp) {
        RequestInfo requestInfo = requestInfos.get(requestURI);

        if(requestInfo == null){
            LOGGER.warn("根据{}没有找到对应处理器",requestURI);
            resolveErrorResponse("404",req,resp);
            return;
        }

        try {
            doHandle(requestURI,requestInfo,req,resp);
        } catch (Exception e) {
            LOGGER.error("根据请求处理出错",e);
            Throwable cause = e.getCause();
            if(cause instanceof AuthorizationException){
                resolveForbiddenResponse((AuthorizationException)cause,req,resp);
            }else  if(e instanceof AuthorizationException){
                resolveForbiddenResponse((AuthorizationException)e,req,resp);
            }else{
                resolveErrorResponse("500",req,resp);
            }
        }
    }

    public void doHandle(String requestURI, RequestInfo requestInfo, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Object service = requestInfo.getTarget();
        Method method = requestInfo.getMethod();

        Object[] params = resolveRequestParameters(requestInfo,request,response);

        Object result = method.invoke(service, params);

        handleResponse(result, ReturnType.of(parseRequestUri(requestURI)),request,response);
    }

    private String parseRequestUri(String uri){
        if(uri.endsWith(".html")){
            return "html";
        }else if(uri.endsWith(".json")){
            return "json";
        }
        return null;
    }

    private void handleResponse(Object result, ReturnType returnType, HttpServletRequest request, HttpServletResponse response) {
        switch (returnType){
            case html:
                resolveHtmlResponse(result,request,response);
                break;
            case json:
                resolveJsonResponse(result,request,response);
                break;
        }
    }

    private void resolveHtmlResponse(Object result, HttpServletRequest request, HttpServletResponse response) {
        if(result != null && result instanceof String){
            String path = result.toString();
            try {
                if(path.startsWith("redirect:")){
                    response.sendRedirect(buildPage(path.substring("redirect:".length())));
                }else{
                    request.getRequestDispatcher(buildPage(path)).forward(request,response);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void resolveErrorResponse(String code, HttpServletRequest req, HttpServletResponse resp) {
        String page = "error/"+code.substring(0, 1) + "xx";
        resolveHtmlResponse(page,req,resp);
    }
    private void resolveForbiddenResponse(AuthorizationException ex, HttpServletRequest req, HttpServletResponse resp) {
        String page = "forbidden/403";
        resolveHtmlResponse(page,req,resp);
    }


    private String buildPage(String path) {
        return prefix + path + suffix;
    }

    private void resolveJsonResponse(Object result, HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson().newBuilder().create();
        String jsonResult = gson.toJson(toResponseResult(result));

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.println(jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseResult toResponseResult(Object result) {
        if(result != null && result instanceof ResponseResult){
            return (ResponseResult) result;
        }
        return ResponseResult.ok(result);
    }

    private Object[] resolveRequestParameters(RequestInfo requestInfo, HttpServletRequest request, HttpServletResponse response) {
        Class[] params = requestInfo.getParams();
        if(params == null || params.length ==0){
            return new Object[]{};
        }else{
            List<Object> parameters = new ArrayList<>();
            for (Class paramClazz : params) {
                parameters.add(resolveServiceParam(paramClazz,request,response));
            }
            return parameters.toArray(new Object[0]);
        }
    }

    private Object resolveServiceParam(Class<?> paramClazz, HttpServletRequest request, HttpServletResponse response) {
        if(ServletRequest.class.isAssignableFrom(paramClazz)){
            return request;
        }else if(ServletResponse.class.isAssignableFrom(paramClazz)){
            return response;
        }
        // todo ...
        return null;
    }
}
