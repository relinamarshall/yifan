package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.upms.biz.service.SysPostService;
import com.yifan.upms.core.entity.SysPost;
import com.yifan.upms.core.vo.PostExcelVo;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysPostController
 * <p>
 * 岗位管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "岗位管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 获取岗位列表
     *
     * @return 岗位列表
     */
    @GetMapping("/list")
    public R<List<SysPost>> listPosts() {
        return R.ok(sysPostService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return R
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys_post_get')")
    public R getSysPostPage(Page page) {
        return R.ok(sysPostService.page(page, Wrappers.<SysPost>lambdaQuery().orderByAsc(SysPost::getPostSort)));
    }

    /**
     * 通过id查询岗位信息表
     *
     * @param postId id
     * @return R
     */
    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{postId}")
    @PreAuthorize("@pms.hasPermission('sys_post_get')")
    public R getById(@PathVariable("postId") Long postId) {
        return R.ok(sysPostService.getById(postId));
    }

    /**
     * 新增岗位信息表
     *
     * @param sysPost 岗位信息表
     * @return R
     */
    @Operation(summary = "新增岗位信息表", description = "新增岗位信息表")
    @SysLog("新增岗位信息表")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_post_add')")
    public R save(@RequestBody SysPost sysPost) {
        return R.ok(sysPostService.save(sysPost));
    }

    /**
     * 修改岗位信息表
     *
     * @param sysPost 岗位信息表
     * @return R
     */
    @Operation(summary = "修改岗位信息表", description = "修改岗位信息表")
    @SysLog("修改岗位信息表")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_post_edit')")
    public R updateById(@RequestBody SysPost sysPost) {
        return R.ok(sysPostService.updateById(sysPost));
    }

    /**
     * 通过id删除岗位信息表
     *
     * @param postId id
     * @return R
     */
    @Operation(summary = "通过id删除岗位信息表", description = "通过id删除岗位信息表")
    @SysLog("通过id删除岗位信息表")
    @DeleteMapping("/{postId}")
    @PreAuthorize("@pms.hasPermission('sys_post_del')")
    public R removeById(@PathVariable Long postId) {
        return R.ok(sysPostService.removeById(postId));
    }

    /**
     * 导出excel 表格
     *
     * @return List<PostExcelVo>
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('sys_post_import_export')")
    public List<PostExcelVo> export() {
        return sysPostService.listPost();
    }

    /**
     * 导入岗位
     *
     * @param excelVOList   岗位列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    @PostMapping("/import")
    @PreAuthorize("@pms.hasPermission('sys_post_import_export')")
    public R importRole(@RequestExcel List<PostExcelVo> excelVOList, BindingResult bindingResult) {
        return sysPostService.importPost(excelVOList, bindingResult);
    }
}

