package com.sucls.security.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.*;

/**
 * @author sucl
 * @date 2022/7/7 10:31
 * @since 1.0.0
 */
public class RealmBuilder {

    public static RealmBuilder create(){
        return new RealmBuilder();
    }

    public InMemoryConfigurationRealm inMemoryRealm(){
        return new InMemoryConfigurationRealm();
    }

    /**
     *
     */
    public static class InMemoryConfigurationRealm extends AuthorizingRealm {

        private Map<String,UserInfo> users = new HashMap<>();

        public UserInfo user(String user){
            UserInfo userInfo = new UserInfo(this,user);
            users.put(user,userInfo);
            return userInfo;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            Object principal = authenticationToken.getPrincipal();
            if(!users.containsKey(principal)){
                throw new UnknownAccountException(String.format("账号【%s】不存在",principal));
            }
            UserInfo userInfo = users.get(principal);
//            if(!Objects.equals(authenticationToken.getCredentials(),userInfo.password)){
//                throw new IncorrectCredentialsException("密码错误");
//            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal,userInfo.password,"");
            return authenticationInfo;
        }

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
            if(users.containsKey(primaryPrincipal)){
                UserInfo userInfo = users.get(primaryPrincipal);
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(new HashSet<>(userInfo.roles) );
                authorizationInfo.addStringPermissions(userInfo.permissions);
                return authorizationInfo;
            }
            return null;
        }

    }

    public static class UserInfo{
        private String user;
        private String password;
        private List<String> roles;
        private List<String> permissions;

        private InMemoryConfigurationRealm realm;

        public UserInfo(InMemoryConfigurationRealm realm, String user) {
            this.realm = realm;
            this.user = user;
        }

        public InMemoryConfigurationRealm and(){
            return realm;
        }

        public InMemoryConfigurationRealm build(){
            return realm;
        }

        public UserInfo password(String password) {
            this.password = password;
            return this;
        }

        public UserInfo role(String role) {
            if(this.roles == null){
                this.roles = new ArrayList<>();
            }
            this.roles.add(role);
            return this;
        }

        public UserInfo permission(String permission) {
            if(this.permissions == null){
                this.permissions = new ArrayList<>();
            }
            this.permissions.add(permission);
            return this;
        }

        public UserInfo permissions(List<String> permissions) {
            if(this.permissions == null){
                this.permissions = new ArrayList<>();
            }
            this.permissions.addAll(permissions);
            return this;
        }

    }

}
