package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.dto.SysLogDto;
import com.yifan.upms.core.entity.SysLog;

import java.util.List;

/**
 * SysLogService
 * <p>
 * 日志表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页查询日志
     *
     * @param page   Page
     * @param sysLog SysLogDto
     * @return Page<SysLog>
     */
    Page<SysLog> getLogByPage(Page page, SysLogDto sysLog);

    /**
     * 列表查询日志
     *
     * @param sysLog 查询条件
     * @return List
     */
    List<SysLog> getLogList(SysLogDto sysLog);
}
