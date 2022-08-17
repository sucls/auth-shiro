package com.sucls.security.security;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.Arrays;

/**
 * @author sucl
 * @date 2022/7/6 21:09
 * @since 1.0.0
 */
public class SimpleEnvironmentLoaderListener extends EnvironmentLoaderListener {

    @Override
    protected void customizeEnvironment(WebEnvironment environment) {
        if(environment instanceof DefaultWebEnvironment){
            DefaultWebEnvironment webEnvironment = (DefaultWebEnvironment) environment;
            // 添加FilterChainResolver
            PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
            configureFilterChainResolver(filterChainResolver);
            webEnvironment.setFilterChainResolver(filterChainResolver);
            // 添加SecurityManager
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            configureWebSecurityManager(securityManager);
            webEnvironment.setSecurityManager(securityManager);
        }
    }

    private void configureFilterChainResolver(PathMatchingFilterChainResolver filterChainResolver) {
        FormAuthenticationFilter authcFilter = (FormAuthenticationFilter) filterChainResolver.getFilterChainManager().getFilters().get(DefaultFilter.authc.name());
        authcFilter.setLoginUrl("/login.jsp");
        authcFilter.setSuccessUrl("/index.html");

        filterChainResolver.getFilterChainManager().addToChain("/webjars/**", DefaultFilter.anon.name());
        filterChainResolver.getFilterChainManager().addToChain("/assets/**", DefaultFilter.anon.name());

        filterChainResolver.getFilterChainManager().addToChain("/**", DefaultFilter.authc.name());
    }

    private void configureWebSecurityManager(DefaultWebSecurityManager securityManager) {

//        securityManager.setSessionManager();
//
        securityManager.setAuthenticator(newAuthenticator());
//
//        securityManager.setSubjectDAO();
//
//        securityManager.setRememberMeManager();
//
//        securityManager.setCacheManager();

        securityManager.setRealms(Arrays.asList(initRealm()));
    }

    private Authenticator newAuthenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy()); // 至少一个Realm
        authenticator.setRealms(Arrays.asList(initRealm()) );
        return authenticator;
    }

    private Realm initRealm(){
        return RealmBuilder.create()
                .inMemoryRealm()
                    .user("admin").password("123456").role("ROLE_ADMIN").permissions(Arrays.asList("read","edit","create","delete"))
                .and()
                    .user("user").password("123456").role("ROLE_USER").permission("read")
                .build();
    }
}
