package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysDict;

/**
 * SysDictService
 * <p>
 * 字典表
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 根据ID 删除字典
     *
     * @param id Long
     */
    void removeDict(Long id);

    /**
     * 更新字典
     *
     * @param sysDict 字典
     */
    void updateDict(SysDict sysDict);

    /**
     * 清除缓存
     */
    void clearDictCache();
}

