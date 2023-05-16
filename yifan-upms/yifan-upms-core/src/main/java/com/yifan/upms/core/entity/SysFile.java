package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yifan.common.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysFile
 * <p>
 * 文件管理
 *
 * @author Wenzhou
 * @since 2023/5/8 15:30
 */
@Data
@TableName("sys_file")
@Schema(description = "文件")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原文件名
     */
    private String original;

    /**
     * 容器名称
     */
    private String bucketName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 删除标识：1-删除，0-正常
     */
    @TableLogic
    private Integer delFlag;
}
