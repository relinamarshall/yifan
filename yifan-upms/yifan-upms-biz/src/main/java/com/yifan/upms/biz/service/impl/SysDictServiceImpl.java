package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.common.core.constant.CacheConstants;
import com.yifan.common.core.constant.enums.DictTypeEnum;
import com.yifan.common.core.exception.ErrorCodes;
import com.yifan.common.core.util.MsgUtils;
import com.yifan.upms.biz.mapper.SysDictItemMapper;
import com.yifan.upms.biz.mapper.SysDictMapper;
import com.yifan.upms.biz.service.SysDictService;
import com.yifan.upms.core.entity.SysDict;
import com.yifan.upms.core.entity.SysDictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * SysDictServiceImpl
 * <p>
 * 字典表
 *
 * @author Wenzhou
 * @since 2023/5/12 12:42
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param id 字典ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void removeDict(Long id) {
        SysDict dict = this.getById(id);
        // 系统内置
        Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
                MsgUtils.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
        baseMapper.deleteById(id);
        dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#dict.dictKey")
    public void updateDict(SysDict dict) {
        SysDict sysDict = this.getById(dict.getId());
        // 系统内置
        Assert.state(!DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystemFlag()),
                MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
        this.updateById(dict);
    }

    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public void clearDictCache() {

    }
}

