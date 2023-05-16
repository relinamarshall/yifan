package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysDeptRelation
 * <p>
 * 部门关系表
 *
 * @author Wenzhou
 * @since 2023/5/8 15:28
 */
@Data
@TableName("sys_dept_rela")
@Schema(description = "部门关系")
@EqualsAndHashCode(callSuper = true)
public class SysDeptRela extends Model<SysDeptRela> {
    private static final long serialVersionUID = 1L;

    /**
     * 祖先节点
     */
    @Schema(description = "祖先节点")
    private Long ancestor;

    /**
     * 后代节点
     */
    @Schema(description = "后代节点")
    private Long descendant;
}
