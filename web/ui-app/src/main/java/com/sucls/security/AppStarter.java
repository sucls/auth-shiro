package com.sucls.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class AppStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);

    public static void init(){
        Environment environment = new BasicIniEnvironment("classpath:shiro.ini");
        SecurityManager securityManager = environment.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);
    }

    /**
     *
     * @param user
     * @param pwd
     */
    public static void login(String user, String pwd){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            LOGGER.warn("用户【{}】已经登录",subject.getPrincipal());
            return;
        }
        AuthenticationToken token = new UsernamePasswordToken(user,pwd);
        subject.login(token);
        if(subject.isAuthenticated()){
            LOGGER.info("用户【{}】登录登录成功",subject.getPrincipal());
        }
    }

    public static void add(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            LOGGER.error("用户没有登录登录");
            return;
        }
        if(!subject.hasRole("ROLE_ADMIN")){
            LOGGER.error("当前用户【{}】，没有角色【{}】，不可操作",subject.getPrincipal(), "ROLE_ADMIN");
            return;
        }
        if(!subject.isPermitted("add")){
            LOGGER.error("当前用户【{}】，没有权限【{}】，不可操作",subject.getPrincipal(), "add");
            return;
        }
    }

    public static void find(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            LOGGER.error("用户没有登录登录");
            return;
        }
        if(!subject.hasRole("ROLE_USER")){
            LOGGER.error("当前用户【{}】，没有角色【{}】，不可操作",subject.getPrincipal(), "ROLE_USER");
            return;
        }
        if(!subject.isPermitted("test:find")){
            LOGGER.error("当前用户【{}】，没有权限【{}】，不可操作",subject.getPrincipal(), "find");
            return;
        }
    }

    public static void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("----------------------- 初始化 ----------------------");
        init();

        LOGGER.info("----------------------- 执行find操作 ----------------------");
        find();

        LOGGER.info("----------------------- 用户admin登录 ----------------------");
        login("admin","123456");
        LOGGER.info("----------------------- 用户admin执行add操作 ----------------------");
        add();
        LOGGER.info("----------------------- 用户admin执行find操作 ----------------------");
        find();
        LOGGER.info("----------------------- 用户admin登出 ----------------------");
        logout();

        Thread.sleep(TimeUnit.SECONDS.toSeconds(1));

        LOGGER.info("----------------------- 用户user登录 ----------------------");
        login("user","123456");
        LOGGER.info("----------------------- 用户user执行add操作 ----------------------");
        add();
        LOGGER.info("----------------------- 用户user执行find操作 ----------------------");
        find();
        LOGGER.info("----------------------- 用户user登出 ----------------------");
        logout();

        System.exit(0);
    }
}
