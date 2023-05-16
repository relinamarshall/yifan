package com.yifan.upms.core.vo;

import com.yifan.upms.core.entity.SysUser;

import java.io.Serializable;

import lombok.Data;

/**
 * UserInfoVo
 *
 * @author Wenzhou
 * @since 2023/5/12 11:27
 */
@Data
public class UserInfoVo implements Serializable {

    /**
     * 用户基本信息
     */
    private SysUser sysUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Long[] roles;

}

