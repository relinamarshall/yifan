package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.common.core.constant.CacheConstants;
import com.yifan.upms.biz.mapper.SysOauthClientDetailsMapper;
import com.yifan.upms.biz.service.SysOauthClientDetailsService;
import com.yifan.upms.core.entity.SysOauthClientDetails;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * SysOauthClientDetailsServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:47
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails>
        implements SysOauthClientDetailsService {

    /**
     * 通过ID删除客户端
     *
     * @param id String
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#id")
    public Boolean removeClientDetailsById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据客户端信息
     *
     * @param clientDetails SysOauthClientDetails
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
    public Boolean updateClientDetailsById(SysOauthClientDetails clientDetails) {
        return this.updateById(clientDetails);
    }

    /**
     * 清除客户端缓存
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
    public void clearClientCache() {

    }
}
