package com.yifan.upms.core.feign;

import com.yifan.common.core.constant.SecurityConstants;
import com.yifan.common.core.constant.ServiceNameConstants;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.dto.UserInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * RemoteUserService
 *
 * @author Wenzhou
 * @since 2023/5/8 16:00
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return R
     */
    @GetMapping(value = "/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
    R<UserInfo> info(@PathVariable("username") String username);

    /**
     * 通过手机号码查询用户、角色信息
     *
     * @param phone 手机号码
     * @param from  调用标志
     * @return R
     */
    @GetMapping(value = "/app/info/{phone}", headers = SecurityConstants.HEADER_FROM_IN)
    R<UserInfo> infoByMobile(@PathVariable("phone") String phone);

    /**
     * 根据部门id，查询对应的用户 id 集合
     *
     * @param deptIds 部门id 集合
     * @param from    调用标志
     * @return 用户 id 集合
     */
    @GetMapping(value = "/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
    R<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds);

}

