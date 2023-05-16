package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysRoleMenu
 * <p>
 * 角色菜单表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:46
 */
@Data
@TableName("sys_role_menu")
@Schema(description = "角色菜单")
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单id")
    private Long menuId;

}
