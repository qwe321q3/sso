# sso
 单点登录框架

# SpringBoot 客户端配置

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

# SpringMVC 客户端配置

```
 <filter>
        <filter-name>AuthenticationFilter</filter-name>
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

> SSO服务端票据保存策略描述
- 开发环境可以使用内存保存ticket
- 生产环境可以使用集群redis来保存ticket
- 自定义票据保存可以实现TicketRegistry接口(如果mongodb,Ecache,mysql)等
- 默认使用内存模式方便环境测试

```
/**
 * 票据信息注册内存实现
 *
 * @author tianshuo
 */
@Slf4j
@Service
public final class InMemoryRegistry implements TicketRegistry {

}
```
- 使用redis时 请放开RedisConfig的注释
```$xslt
@Configurable
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //key使用StringRedisSerializer序列化
        redisTemplate.setKeySerializer(redisSerializer);
        //value使用jackson2JsonRedisSerializer序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

}
```
- RedisRegistry类增加@Service注解,同时增加redis集群配置
```$xslt
@Slf4j
//@Service
public final class RedisRegistry implements TicketRegistry {
}
```
- Redis配置项
```$xslt
# redis config
spring.redis.password=
spring.redis.cluster.nodes=192.168.1.11:46380,192.168.1.11:46379,192.168.1.10:46380,192.168.1.10:46379
#spring.redis.database=0
spring.redis.cluster.max-redirects=3
spring.redis.lettuce.pool.max-active=32
spring.redis.lettuce.pool.max-idle=12
spring.redis.lettuce.pool.min-idle=8
```
> 单点登录流程
![单点登录](http://i2.tiimg.com/707625/3ac5cf833640d58f.jpg "单点登录")
   
> 单点登出流程
![单点登出](http://i2.tiimg.com/707625/a693176896ca567e.jpg "单点登出")

