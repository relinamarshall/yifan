package com.yifan.gateway.config;

import com.yifan.gateway.handler.ImageCodeHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RouterFunctionConfiguration
 * <p>
 * 路由配置信息
 *
 * @author Wenzhou
 * @since 2023/5/11 10:58
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RouterFunctionConfiguration {

    private final ImageCodeHandler imageCodeHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.path("/code")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeHandler);
    }
}
