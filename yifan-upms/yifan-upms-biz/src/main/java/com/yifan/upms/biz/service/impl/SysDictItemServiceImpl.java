package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.common.core.constant.CacheConstants;
import com.yifan.common.core.constant.enums.DictTypeEnum;
import com.yifan.common.core.exception.ErrorCodes;
import com.yifan.common.core.util.MsgUtils;
import com.yifan.upms.biz.mapper.SysDictItemMapper;
import com.yifan.upms.biz.service.SysDictItemService;
import com.yifan.upms.biz.service.SysDictService;
import com.yifan.upms.core.entity.SysDict;
import com.yifan.upms.core.entity.SysDictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * SysDictItemServiceImpl
 * <p>
 * 字典项
 *
 * @author Wenzhou
 * @since 2023/5/12 11:38
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    private final SysDictService dictService;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void removeDictItem(Long id) {
        // 根据ID查询字典ID
        SysDictItem dictItem = this.getById(id);
        SysDict dict = dictService.getById(dictItem.getDictId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
                MsgUtils.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
        this.removeById(id);
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#item.type")
    public void updateDictItem(SysDictItem item) {
        // 查询字典
        SysDict dict = dictService.getById(item.getDictId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
                MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
        this.updateById(item);
    }
}
