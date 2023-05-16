package com.yifan.common.log;

import com.yifan.common.log.aspect.SysLogAspect;
import com.yifan.common.log.aspect.SysLogListener;
import com.yifan.upms.core.feign.RemoteLogService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.RequiredArgsConstructor;

/**
 * LogAutoConfiguration
 *
 * @author Wenzhou
 * @since 2023/5/6 12:22
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    @Bean
    public SysLogListener sysLogListener(RemoteLogService remoteLogService) {
        return new SysLogListener(remoteLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

}
