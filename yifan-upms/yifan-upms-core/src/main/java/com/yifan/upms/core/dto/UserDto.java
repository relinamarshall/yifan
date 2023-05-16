package com.yifan.upms.core.dto;

import com.yifan.upms.core.entity.SysUser;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserDto
 *
 * @author Wenzhou
 * @since 2023/5/12 10:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends SysUser {
    /**
     * 角色ID
     */
    private List<Long> role;

    private Long deptId;

    /**
     * 岗位ID
     */
    private List<Long> post;

    /**
     * 新密码
     */
    private String newpassword1;

    /**
     * 验证码
     */
    private String code;
}