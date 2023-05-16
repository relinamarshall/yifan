package com.yifan.upms.core.feign;

import com.yifan.common.core.constant.SecurityConstants;
import com.yifan.common.core.constant.ServiceNameConstants;
import com.yifan.common.core.util.R;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * RemoteDeptService
 *
 * @author Wenzhou
 * @since 2023/5/8 15:57
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDeptService {

    /**
     * 查收子级id列表
     *
     * @param deptId Long
     * @return 返回子级id列表
     */
    @GetMapping(value = "/dept/child-id/{deptId}", headers = SecurityConstants.HEADER_FROM_IN)
    R<List<Long>> listChildDeptId(@PathVariable("deptId") Long deptId);
}