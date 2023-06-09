package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.common.security.annotation.Inner;
import com.yifan.upms.biz.service.SysPublicParamService;
import com.yifan.upms.core.entity.SysPublicParam;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysPublicParamController
 * <p>
 * 公共参数
 *
 * @author Wenzhou
 * @since 2023/5/12 11:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/param")
@Tag(name = "公共参数配置")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysPublicParamController {

    private final SysPublicParamService sysPublicParamService;

    /**
     * 通过key查询公共参数值
     *
     * @param publicKey String
     * @return R
     */
    @Inner(value = false)
    @Operation(summary = "查询公共参数值", description = "根据key查询公共参数值")
    @GetMapping("/publicValue/{publicKey}")
    public R publicKey(@PathVariable("publicKey") String publicKey) {
        return R.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
    }

    /**
     * 分页查询
     *
     * @param page           分页对象
     * @param sysPublicParam 公共参数
     * @return R
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R getSysPublicParamPage(Page page, SysPublicParam sysPublicParam) {
        return R.ok(sysPublicParamService.page(page,
                Wrappers.<SysPublicParam>lambdaQuery()
                        .like(CharSequenceUtil.isNotBlank(sysPublicParam.getPublicName()), SysPublicParam::getPublicName,
                                sysPublicParam.getPublicName())
                        .like(CharSequenceUtil.isNotBlank(sysPublicParam.getPublicKey()), SysPublicParam::getPublicKey,
                                sysPublicParam.getPublicKey())
                        .orderByDesc(SysPublicParam::getCrtTm)));
    }

    /**
     * 通过id查询公共参数
     *
     * @param publicId id
     * @return R
     */
    @Operation(summary = "通过id查询公共参数", description = "通过id查询公共参数")
    @GetMapping("/{publicId}")
    public R getById(@PathVariable("publicId") Long publicId) {
        return R.ok(sysPublicParamService.getById(publicId));
    }

    /**
     * 新增公共参数
     *
     * @param sysPublicParam 公共参数
     * @return R
     */
    @Operation(summary = "新增公共参数", description = "新增公共参数")
    @SysLog("新增公共参数")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_publicparam_add')")
    public R save(@RequestBody SysPublicParam sysPublicParam) {
        return R.ok(sysPublicParamService.save(sysPublicParam));
    }

    /**
     * 修改公共参数
     *
     * @param sysPublicParam 公共参数
     * @return R
     */
    @Operation(summary = "修改公共参数", description = "修改公共参数")
    @SysLog("修改公共参数")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_publicparam_edit')")
    public R updateById(@RequestBody SysPublicParam sysPublicParam) {
        return sysPublicParamService.updateParam(sysPublicParam);
    }

    /**
     * 通过id删除公共参数
     *
     * @param publicId id
     * @return R
     */
    @Operation(summary = "删除公共参数", description = "删除公共参数")
    @SysLog("删除公共参数")
    @DeleteMapping("/{publicId}")
    @PreAuthorize("@pms.hasPermission('sys_publicparam_del')")
    public R removeById(@PathVariable Long publicId) {
        return sysPublicParamService.removeParam(publicId);
    }

    /**
     * 同步参数
     *
     * @return R
     */
    @SysLog("同步参数")
    @PutMapping("/sync")
    public R sync() {
        return sysPublicParamService.syncParamCache();
    }

}
