package com.sucls.security.core.handle;

import com.sucls.security.aop.AopUtils;
import com.sucls.security.core.Path;
import com.sucls.security.core.RequestInfo;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sucl
 * @date 2022/6/28 15:23
 * @since 1.0.0
 */
public class AnnotationRequestInfoParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationRequestInfoParser.class);

    public Map<String, RequestInfo> parseAnnotation() {
        Map<String, RequestInfo> requestInfos = new HashMap<>();

        Reflections reflections = new Reflections();
        Set<Class<?>> serviceClazzs = reflections.getTypesAnnotatedWith(Path.class);

        if(!serviceClazzs.isEmpty()){
            for (Class<?> serviceClazz : serviceClazzs) {
                Path servicePath = serviceClazz.getAnnotation(Path.class);

                Set<Method> methods = ReflectionUtils.getMethods(serviceClazz, method -> method.getAnnotation(Path.class) != null);
                if(!methods.isEmpty()){
                    for (Method method : methods) {
                        String requestPath = ("/".equals(servicePath.value())?"":servicePath.value()) + method.getAnnotation(Path.class).value();
                        if(requestInfos.containsKey(requestPath)){
                            LOGGER.warn("存在重复服务:{}", requestPath);
                        }

                        LOGGER.info("解析请求类:{}.{} 服务:{}",serviceClazz,method.getName(), requestPath);

                        Object target = getServiceInstance(serviceClazz);
                        requestInfos.put(requestPath,new RequestInfo(target, method, method.getParameterTypes()));
                    }
                }
            }
        }
        return requestInfos;
    }

    private Object getServiceInstance(Class<?> serviceClazz) {
//        try {
//            return serviceClazz.newInstance();
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        return AopUtils.createObject(serviceClazz);
    }

}
