package com.yifan.common.log.event;

import com.yifan.upms.core.entity.SysLog;

import org.springframework.context.ApplicationEvent;

/**
 * SysLogEvent
 * <p>
 * 系统日志事件
 *
 * @author Wenzhou
 * @since 2023/5/6 12:40
 */
public class SysLogEvent extends ApplicationEvent {
    /**
     * SysLogEvent
     *
     * @param source SysLog
     */
    public SysLogEvent(SysLog source) {
        super(source);
    }
}
