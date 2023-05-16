package com.yifan.common.log.aspect;

import com.yifan.common.log.event.SysLogEvent;
import com.yifan.upms.core.entity.SysLog;
import com.yifan.upms.core.feign.RemoteLogService;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SysLogListener
 * <p>
 * 异步监听日志事件
 *
 * @author Wenzhou
 * @since 2023/5/6 12:41
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {
    /**
     * remoteLogService
     */
    private final RemoteLogService remoteLogService;

    /**
     * saveSysLog
     *
     * @param event SysLogEvent
     */
    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        remoteLogService.saveLog(sysLog);
    }
}
