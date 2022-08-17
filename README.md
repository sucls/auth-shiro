## 基于Shiro的示例

  基于 shiro 构建的web项目，改项目为Web mvc项目，基于servlet3.1 + jsp构建。

## 配置
   
 + web.xml
 + Environment
 + Listener

### 配置监听

  配置$$ServletContextListener$$上下文监听，在容器启动后开始初始化Environment
```xml
  <listener>
    <listener-class>com.sucls.security.security.SimpleEnvironmentLoaderListener</listener-class>
  </listener>
```

  主要目的为了构建以下两个对象，并注入到Environment：
   + FilterChainResolver
     - 在FilterChainManager基础上解析用户配置过滤规则，这里需要开发者根据需要配置，最终将配置解析成FilterChain，类似配置如下：
      ```
        /assets/** = anon
        /login = authc
        /admin/** = roles[ROLE_ADMIN]
        /admin/add* = perms[add:*]   //[action:type:instance]
        /** = authc 
      ```
     - 过滤器配置是有序的，当一个请求匹配到多个过滤器时，会依次执行完，最后执行原始的Filter（非ShiroFilter）中
     ```
       /admin/** = anon
       /admin/** = authc
     ```
   + SecurityManager
     - SessionManager
       实现会话管理，当部署集群时，需要通过分布式session管理来实现
     - Authenticator
       认证器，主要提供多种Realm以及相关策略
     - SubjectDAO
       Subject存储
     - RememberMeManager
       提供RememberMe的支持，一般基于Cookies
     - CacheManager
       缓存服务

### 配置WebEnvironment

  配置Environment class类型，在上下文创建后，会根据配置构建WebEnvironment，提供了SecurityManager、FilterChainResolver，为ShiroFilter服务

```xml
  <context-param>
    <param-name>shiroEnvironmentClass</param-name>
    <param-value>org.apache.shiro.web.env.DefaultWebEnvironment</param-value>
  </context-param>
```

### 配置ShiroFilter
 
  所有请求都会经过ShiroFilter，ShiroFilter属于一个FilterChain，通过FilterChainResolver解析用户配置的过滤规则实现认证与鉴权
```html
  <filter>
    <filter-name>shiroFilter</filter-name>
    <filter-class>org.apache.shiro.web.servlet.ShiroFilter</filter-class>
  </filter>

  <filter-mapping>
    <filter-name>shiroFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

## 定义Servlet

  简单的定义了DispatcherServlet，主要处理页面和数据
   + *.html
   + *.json
  
## AOP

  Shiro在AOP这块主要提供了一套简单的抽象解决方法，想通过AOP真正实现访问控制，需要开发者自定义对象代理，或者统一交由IOC容器管理。
  目前提供了以下下几个注解以及对应的处理：
  + @RequiresAuthentication
  + @RequiresGuest
  + @RequiresUser
  + @RequiresRoles
  + @RequiresPermissions

  示例中通过Cglib动态代理，在系统启动时对ServletHandler的目标对象进行代理，结合Shiro提供的方案简单的实现方法层面的访问控制。

### 项目说明

```
   +
    -
    -
```

### 项目目的

   通过该项目，了解基于shiro作为安全框架，shiro可以做什么？开发者需要做什么？
