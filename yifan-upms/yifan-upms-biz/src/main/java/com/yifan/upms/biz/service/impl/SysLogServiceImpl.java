package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.upms.biz.mapper.SysLogMapper;
import com.yifan.upms.biz.service.SysLogService;
import com.yifan.upms.core.dto.SysLogDto;
import com.yifan.upms.core.entity.SysLog;

import org.springframework.stereotype.Service;

import java.util.List;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * SysLogServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:45
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Page getLogByPage(Page page, SysLogDto sysLog) {
        return baseMapper.selectPage(page, buildQueryWrapper(sysLog));
    }

    /**
     * 列表查询日志
     *
     * @param sysLog 查询条件
     * @return List
     */
    @Override
    public List getLogList(SysLogDto sysLog) {
        return baseMapper.selectList(buildQueryWrapper(sysLog));
    }

    /**
     * 构建查询的 wrapper
     *
     * @param sysLog 查询条件
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper buildQueryWrapper(SysLogDto sysLog) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.<SysLog>lambdaQuery()
                .eq(CharSequenceUtil.isNotBlank(sysLog.getType()), SysLog::getType, sysLog.getType())
                .like(CharSequenceUtil.isNotBlank(sysLog.getRemoteAddr()),
                        SysLog::getRemoteAddr, sysLog.getRemoteAddr())
                .orderByDesc(SysLog::getCrtTm);

        if (ArrayUtil.isNotEmpty(sysLog.getCrtTm())) {
            wrapper.ge(SysLog::getCrtTm, sysLog.getCrtTm()[0])
                    .le(SysLog::getCrtTm, sysLog.getCrtTm()[1]);
        }

        return wrapper;
    }

}
