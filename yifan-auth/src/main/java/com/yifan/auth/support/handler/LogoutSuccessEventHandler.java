package com.yifan.auth.support.handler;

import com.yifan.common.core.util.DiyWebUtils;
import com.yifan.common.core.util.SpringContextHolder;
import com.yifan.common.log.event.SysLogEvent;
import com.yifan.common.log.util.SysLogUtils;
import com.yifan.upms.core.entity.SysLog;

import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * LogoutSuccessEventHandler
 * <p>
 * 事件机制处理退出相关
 *
 * @author Wenzhou
 * @since 2023/5/11 15:08
 */
@Slf4j
@Component
public class LogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    public void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
        SysLog logVo = SysLogUtils.getSysLog();
        logVo.setTitle("退出成功");
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();
        logVo.setTime(endTime - startTime);

        // 设置对应的token
        DiyWebUtils.getRequest().ifPresent(request -> logVo.setParams(request.getHeader(HttpHeaders.AUTHORIZATION)));

        // 这边设置ServiceId
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            logVo.setServiceId(authentication.getCredentials().toString());
        }
        logVo.setCrtBy(authentication.getName());
        logVo.setUpdBy(authentication.getName());
        SpringContextHolder.publishEvent(new SysLogEvent(logVo));
    }
}