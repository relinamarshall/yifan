package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.yifan.common.core.util.R;
import com.yifan.common.security.annotation.Inner;
import com.yifan.upms.biz.service.SysLogService;
import com.yifan.upms.core.dto.SysLogDto;
import com.yifan.upms.core.entity.SysLog;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysLogController
 * <p>
 * 日志表 前端控制器
 *
 * @author Wenzhou
 * @since 2023/5/12 11:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
@Tag(name = "日志管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysLogController {

    private final SysLogService sysLogService;

    /**
     * 简单分页查询
     *
     * @param page   分页对象
     * @param sysLog 系统日志
     * @return IPage<SysLog>
     */
    @GetMapping("/page")
    public R<IPage<SysLog>> getLogPage(Page page, SysLogDto sysLog) {
        return R.ok(sysLogService.getLogByPage(page, sysLog));
    }

    /**
     * 删除日志
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("@pms.hasPermission('sys_log_del')")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.ok(sysLogService.removeById(id));
    }

    /**
     * 插入日志
     *
     * @param sysLog 日志实体
     * @return success/false
     */
    @Inner
    @PostMapping
    public R<Boolean> save(@Valid @RequestBody SysLog sysLog) {
        return R.ok(sysLogService.save(sysLog));
    }

    /**
     * 导出excel 表格
     *
     * @param sysLog 查询条件
     * @return EXCEL
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('sys_log_import_export')")
    public List<SysLog> export(SysLogDto sysLog) {
        return sysLogService.getLogList(sysLog);
    }

}
