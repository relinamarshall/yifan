package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysRoleMenu;

/**
 * SysRoleMenuService
 * <p>
 * 角色菜单表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:39
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 更新角色菜单
     *
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return Boolean
     */
    Boolean saveRoleMenus(Long roleId, String menuIds);

}
