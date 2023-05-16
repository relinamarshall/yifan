package com.yifan.common.security.service;

import com.yifan.common.core.constant.CacheConstants;
import com.yifan.common.core.constant.SecurityConstants;
import com.yifan.common.core.util.R;
import com.yifan.common.security.pojo.ExtUser;
import com.yifan.upms.core.dto.UserInfo;
import com.yifan.upms.core.feign.RemoteUserService;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * AppUserDetailsServiceImpl
 * <p>
 * 用户详细信息
 *
 * @author Wenzhou
 * @since 2023/5/9 17:00
 */
@Slf4j
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements ExtUserDetailsService {

    private final RemoteUserService remoteUserService;

    private final CacheManager cacheManager;

    /**
     * 手机号登录
     *
     * @param phone 手机号
     * @return UserDetails
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String phone) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(phone) != null) {
            return (ExtUser) Objects.requireNonNull(cache.get(phone)).get();
        }

        R<UserInfo> result = remoteUserService.infoByMobile(phone);

        UserDetails userDetails = getUserDetails(result);
        if (cache != null) {
            cache.put(phone, userDetails);
        }
        return userDetails;
    }

    /**
     * check-token 使用
     *
     * @param pigUser user
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUser(ExtUser pigUser) {
        return this.loadUserByUsername(pigUser.getPhone());
    }

    /**
     * 是否支持此客户端校验
     *
     * @param clientId 目标客户端
     * @return true/false
     */
    @Override
    public boolean support(String clientId, String grantType) {
        return SecurityConstants.APP.equals(grantType);
    }

}

