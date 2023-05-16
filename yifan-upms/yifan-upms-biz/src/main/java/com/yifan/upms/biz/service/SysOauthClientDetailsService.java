package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysOauthClientDetails;

/**
 * SysOauthClientDetailsService
 * <p>
 * 客户端认证 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

    /**
     * 通过ID删除客户端
     *
     * @param id String
     * @return Boolean
     */
    Boolean removeClientDetailsById(String id);

    /**
     * 修改客户端信息
     *
     * @param sysOauthClientDetails SysOauthClientDetails
     * @return Boolean
     */
    Boolean updateClientDetailsById(SysOauthClientDetails sysOauthClientDetails);

    /**
     * 清除客户端缓存
     */
    void clearClientCache();

}