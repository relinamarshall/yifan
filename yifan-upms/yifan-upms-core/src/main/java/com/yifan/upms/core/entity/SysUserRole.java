package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysUserRole
 * <p>
 * 用户角色表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:49
 */
@Data
@TableName("sys_user_role")
@Schema(description = "用户角色")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 角色ID
     */
    @Schema(description = "角色id")
    private Long roleId;

}

