package com.sucls.security.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.DefaultAnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.aop.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2022/7/7 20:49
 * @since 1.0.0
 */
public class AopUtils {

    /**
     * 创建代理对象，可基于shiro 注解实现鉴权
     * @param type
     * @return
     * @param <T>
     */
    public static <T> T createObject(Class<T> type){
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(new TargetMethodInterceptor());
        return (T) enhancer.create();
    }

    public static class TargetMethodInterceptor extends
            AnnotationsAuthorizingMethodInterceptor implements MethodInterceptor {

        public TargetMethodInterceptor() {
            List<AuthorizingAnnotationMethodInterceptor> interceptors = new ArrayList<AuthorizingAnnotationMethodInterceptor>(5);

            AnnotationResolver resolver = new DefaultAnnotationResolver();
            interceptors.add(new RoleAnnotationMethodInterceptor(resolver));
            interceptors.add(new PermissionAnnotationMethodInterceptor(resolver));
            interceptors.add(new AuthenticatedAnnotationMethodInterceptor(resolver));
            interceptors.add(new UserAnnotationMethodInterceptor(resolver));
            interceptors.add(new GuestAnnotationMethodInterceptor(resolver));

            setMethodInterceptors(interceptors);
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            return super.invoke(createMethodInvocation(target, method, args, methodProxy));
        }

        private MethodInvocation createMethodInvocation(Object target, Method method, Object[] args, MethodProxy methodProxy) {
            return new MethodInvocation() {
                @Override
                public Object proceed() throws Throwable {
                    return methodProxy.invokeSuper(target, args);
                }

                @Override
                public Method getMethod() {
                    return method;
                }

                @Override
                public Object[] getArguments() {
                    return args;
                }

                @Override
                public Object getThis() {
                    return target;
                }
            };
        }

    }
}
