package com.yifan.common.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseEntity
 * <p>
 * 抽象实体
 *
 * @author Wenzhou
 * @since 2023/5/5 12:11
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String crtBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime crtTm;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updTm;
}
