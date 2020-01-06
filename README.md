# sso
 单点登录框架

# springboot 客户端配置

```
   @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AuthenticationFilter());
        Map<String, String> initMap = new HashMap<>();
        initMap.put("pattern", "/js/*|/img/*|/static/*|/css/*|/test/*");
        initMap.put("patternType", "regex");
        initMap.put("ssoServerUrlPrefix", "http://localhost");
        bean.setInitParameters(initMap);
        bean.addUrlPatterns("/*");
        bean.setOrder(5);
        return bean;
    }
```

# springmvc 客户端配置

```
 <filter>
        <filter-name>CAS Authentication Filter</filter-name>
        <filter-class>com.tianshuo.beta.sso.client.authentication.AuthenticationFilter</filter-class>
        <init-param>
            <param-name>ssoServerUrlPrefix</param-name>
            <param-value>http://localhost</param-value>
        </init-param>
        <init-param>
            <param-name>patternType</param-name>
            <param-value>regex</param-value>
        </init-param>
	       <init-param>
            <param-name>pattern</param-name>
            <param-value>/js/*|/img/*|/static/*|/css/*|/test/*</param-value>
        </init-param>
    </filter>
```
