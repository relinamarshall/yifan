package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysMenu;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * SysMenuMapper
 * <p>
 * 菜单权限表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:08
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return Set<SysMenu>
     */
    Set<SysMenu> listMenusByRoleId(Long roleId);
}