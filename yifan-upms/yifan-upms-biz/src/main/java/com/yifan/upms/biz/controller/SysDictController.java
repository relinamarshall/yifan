package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yifan.common.core.constant.CacheConstants;
import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.upms.biz.service.SysDictItemService;
import com.yifan.upms.biz.service.SysDictService;
import com.yifan.upms.core.entity.SysDict;
import com.yifan.upms.core.entity.SysDictItem;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import java.util.List;

import javax.validation.Valid;

import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysDictController
 * <p>
 * 字典表 前端控制器
 *
 * @author Wenzhou
 * @since 2023/5/12 10:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysDictController {

    private final SysDictItemService sysDictItemService;

    private final SysDictService sysDictService;

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/{id:\\d+}")
    public R<SysDict> getById(@PathVariable Long id) {
        return R.ok(sysDictService.getById(id));
    }

    /**
     * 分页查询字典信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage<SysDict>> getDictPage(Page page, SysDict sysDict) {
        return R.ok(sysDictService.page(page, Wrappers.<SysDict>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(sysDict.getDictKey()), SysDict::getDictKey, sysDict.getDictKey())));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param key 类型
     * @return 同类型字典
     */
    @GetMapping("/key/{key}")
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#key")
    public R<List<SysDictItem>> getDictByKey(@PathVariable String key) {
        return R.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictKey, key)));
    }

    /**
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @SysLog("添加字典")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dict_add')")
    public R<Boolean> save(@Valid @RequestBody SysDict sysDict) {
        return R.ok(sysDictService.save(sysDict));
    }

    /**
     * 删除字典，并且清除字典缓存
     *
     * @param id ID
     * @return R
     */
    @SysLog("删除字典")
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R removeById(@PathVariable Long id) {
        sysDictService.removeDict(id);
        return R.ok();
    }

    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    @PutMapping
    @SysLog("修改字典")
    @PreAuthorize("@pms.hasPermission('sys_dict_edit')")
    public R updateById(@Valid @RequestBody SysDict sysDict) {
        sysDictService.updateDict(sysDict);
        return R.ok();
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param sysDictItem 字典项
     * @return IPage<SysDictItem>
     */
    @GetMapping("/item/page")
    public R<IPage<SysDictItem>> getSysDictItemPage(Page page, SysDictItem sysDictItem) {
        return R.ok(sysDictItemService.page(page, Wrappers.query(sysDictItem)));
    }

    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/{id:\\d+}")
    public R<SysDictItem> getDictItemById(@PathVariable("id") Long id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @SysLog("新增字典项")
    @PostMapping("/item")
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R<Boolean> save(@RequestBody SysDictItem sysDictItem) {
        return R.ok(sysDictItemService.save(sysDictItem));
    }

    /**
     * 修改字典项
     *
     * @param sysDictItem 字典项
     * @return R
     */
    @SysLog("修改字典项")
    @PutMapping("/item")
    public R updateById(@RequestBody SysDictItem sysDictItem) {
        sysDictItemService.updateDictItem(sysDictItem);
        return R.ok();
    }

    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @SysLog("删除字典项")
    @DeleteMapping("/item/{id:\\d+}")
    public R removeDictItemById(@PathVariable Long id) {
        sysDictItemService.removeDictItem(id);
        return R.ok();
    }

    @SysLog("清除字典缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R clearDictCache() {
        sysDictService.clearDictCache();
        return R.ok();
    }
}

