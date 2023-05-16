package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysUserPost
 * <p>
 * 用户岗位表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:48
 */
@Data
@TableName("sys_user_post")
@Schema(description = "用户岗位")
@EqualsAndHashCode(callSuper = true)
public class SysUserPost extends Model<SysUserPost> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位id")
    private Long postId;

}

