package fs.boot.demo2.config;

import fs.boot.demo2.domain.User;
import fs.boot.demo2.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * Create by fs on 2018/5/16
 * 路由器函数 配置
 */
@Configuration
public class RouterFunctionConfiguration {
    /**
     * Servlet
     * 请求接口：ServletRequest或者HttpServletRequest
     * 响应接口：ServletResponse或者HttpServletResponse
     * Spring 5.0 重新定义了服务请求和响应接口
     * 请求接口：ServerRequest
     * 响应接口：ServerResponse
     * 即可以支持Servlet规范，也可以支持自定义，比如Netty（Web Server）
     * <p>
     * 定义GET请求，并且返回所有的用户对象 URI：/person/find/all
     * Flux 是0-N个对象集合
     * Mono 是0-1个对象集合
     * Reactive 中的Flux或者Mono它是异步处理（非阻塞）
     * 集合对象基本上是同步处理（阻塞）
     * Flux 或者 Mono 都是 Publisher
     */
    @Bean
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRespository userRepository) {
        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),
                request -> {
                    // 返回所有用户对象
                    Collection<User> users = userRepository.findAll();
                    Flux<User> flux = Flux.fromIterable(users);
                    return ServerResponse.ok().body(flux, User.class);
                });
    }
}
