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
 * SysDict
 * <p>
 * 字典表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:29
 */
@Data
@TableName("sys_dict")
@Schema(description = "字典")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "字典编号")
    private Long id;

    /**
     * 类型
     */
    @Schema(description = "字典key")
    private String dictKey;

    /**
     * 描述
     */
    @Schema(description = "字典描述")
    private String description;

    /**
     * 是否是系统内置
     */
    @Schema(description = "是否系统内置")
    private String systemFlag;

    /**
     * 备注信息
     */
    @Schema(description = "备注信息")
    private String remark;

    /**
     * 删除标记
     */
    @TableLogic
    @Schema(description = "删除标记,1:已删除,0:正常")
    private String delFlag;

}
