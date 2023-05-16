package com.yifan.upms.core.vo;

import com.yifan.upms.core.entity.SysPost;
import com.yifan.upms.core.entity.SysRole;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * UserVo
 *
 * @author Wenzhou
 * @since 2023/5/12 10:11
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 创建时间
     */
    private LocalDateTime crtTm;

    /**
     * 修改时间
     */
    private LocalDateTime updTm;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 锁定标记
     */
    private String lockFlag;

    /**
     * 简介
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色列表
     */
    private List<SysRole> roleList;

    /**
     * 岗位列表
     */
    private List<SysPost> postList;
}