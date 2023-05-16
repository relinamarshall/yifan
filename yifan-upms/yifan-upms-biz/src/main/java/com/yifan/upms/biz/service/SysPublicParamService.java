package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysPublicParam;

/**
 * SysPublicParamService
 * <p>
 * 公共参数配置
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysPublicParamService extends IService<SysPublicParam> {
    /**
     * 通过key查询公共参数指定值
     *
     * @param publicKey String
     * @return R
     */
    String getSysPublicParamKeyToValue(String publicKey);

    /**
     * 更新参数
     *
     * @param sysPublicParam SysPublicParam
     * @return R
     */
    R updateParam(SysPublicParam sysPublicParam);

    /**
     * 删除参数
     *
     * @param publicId Long
     * @return R
     */
    R removeParam(Long publicId);

    /**
     * 同步缓存
     *
     * @return R
     */
    R syncParamCache();
}

