package com.yifan.upms.core.feign;

import com.yifan.common.core.constant.SecurityConstants;
import com.yifan.common.core.constant.ServiceNameConstants;
import com.yifan.common.core.util.R;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RemoteParamService
 *
 * @author Wenzhou
 * @since 2023/5/8 15:59
 */
@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteParamService {

    /**
     * 通过key 查询参数配置
     *
     * @param key key
     * @return 参数配置
     */
    @GetMapping(value = "/param/publicValue/{key}", headers = SecurityConstants.HEADER_FROM_IN)
    R<String> getByKey(@PathVariable("key") String key);

}