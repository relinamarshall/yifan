package com.yifan.upms.core.dto;

import com.yifan.upms.core.entity.SysPost;
import com.yifan.upms.core.entity.SysRole;
import com.yifan.upms.core.entity.SysUser;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * UserInfo
 *
 * @author Wenzhou
 * @since 2023/5/8 16:02
 */
@Data
public class UserInfo implements Serializable {

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

    /**
     * 角色集合
     */
    private List<SysRole> roleList;

    /**
     * 岗位集合
     */
    private Long[] posts;

    /**
     * 岗位集合
     */
    private List<SysPost> postList;

}

