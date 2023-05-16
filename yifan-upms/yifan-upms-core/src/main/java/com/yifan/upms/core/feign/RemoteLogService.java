package com.yifan.upms.core.feign;

import com.yifan.common.core.constant.SecurityConstants;
import com.yifan.common.core.constant.ServiceNameConstants;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysLog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * RemoteLogService
 *
 * @author Wenzhou
 * @since 2023/5/6 12:44
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {
    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @return success or false
     */
    @PostMapping(value = "/log", headers = SecurityConstants.HEADER_FROM_IN)
    R<Boolean> saveLog(@RequestBody SysLog sysLog);
}
