package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysDictItem;

/**
 * SysDictItemService
 * <p>
 * 字典项
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    void removeDictItem(Long id);

    /**
     * 更新字典项
     *
     * @param item 字典项
     */
    void updateDictItem(SysDictItem item);

}
