package com.yifan.upms.biz;

import com.yifan.common.feign.annotation.EnableFeignClients;
import com.yifan.common.security.annotation.EnableResourceServer;
import com.yifan.common.swagger.annotation.EnableDoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AdminApplication
 * <p>
 * 统一用户权限管理系统(upms)
 *
 * @author Wenzhou
 * @since 2023/5/12 10:02
 */
@EnableDoc
@EnableResourceServer
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
