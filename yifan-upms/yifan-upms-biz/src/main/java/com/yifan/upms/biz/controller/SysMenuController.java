package com.yifan.upms.biz.controller;

import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.common.security.util.SecurityUtils;
import com.yifan.upms.biz.service.SysMenuService;
import com.yifan.upms.core.entity.SysMenu;

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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysMenuController
 * <p>
 * 菜单管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @param parentId 父节点ID
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R<List<Tree<Long>>> getUserMenu(Long parentId) {
        // 获取符合条件的菜单
        Set<SysMenu> menuSet = SecurityUtils.getRoles()
                .stream()
                .map(sysMenuService::findMenuByRoleId)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return R.ok(sysMenuService.filterMenu(menuSet, parentId));
    }

    /**
     * 返回树形菜单集合
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R<List<Tree<Long>>> getTree(boolean lazy, Long parentId) {
        return R.ok(sysMenuService.treeMenu(lazy, parentId));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/tree/{roleId}")
    public R<List<Long>> getRoleTree(@PathVariable Long roleId) {
        return R
                .ok(sysMenuService.findMenuByRoleId(roleId).stream().map(SysMenu::getMenuId).collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id:\\d+}")
    public R<SysMenu> getById(@PathVariable Long id) {
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return 含ID 菜单信息
     */
    @SysLog("新增菜单")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_add')")
    public R<SysMenu> save(@Valid @RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return R.ok(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @SysLog("删除菜单")
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.ok(sysMenuService.removeMenuById(id));
    }

    /**
     * 更新菜单
     *
     * @param sysMenu SysMenu
     * @return Boolean
     */
    @SysLog("更新菜单")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_edit')")
    public R<Boolean> update(@Valid @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.updateMenuById(sysMenu));
    }

    /**
     * 清除菜单缓存
     */
    @SysLog("清除菜单缓存")
    @DeleteMapping("/cache")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R clearMenuCache() {
        sysMenuService.clearMenuCache();
        return R.ok();
    }

}
