package com.yifan.upms.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yifan.common.mybatis.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysPublicParam
 * <p>
 * 公共参数配置
 *
 * @author Wenzhou
 * @since 2023/5/8 15:45
 */
@Data
@TableName("sys_public_param")
@Schema(description = "公共参数")
@EqualsAndHashCode(callSuper = true)
public class SysPublicParam extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "公共参数编号")
    private Long publicId;

    /**
     * 公共参数名称
     */
    @Schema(description = "公共参数名称", required = true, example = "公共参数名称")
    private String publicName;

    /**
     * 公共参数地址值,英文大写+下划线
     */
    @Schema(description = "键[英文大写+下划线]", required = true, example = "PIGX_PUBLIC_KEY")
    private String publicKey;

    /**
     * 值
     */
    @Schema(description = "值", required = true, example = "999")
    private String publicValue;

    /**
     * 状态（1有效；2无效；）
     */
    @Schema(description = "标识[1有效；2无效]", example = "1")
    private String status;

    /**
     * 公共参数编码
     */
    @Schema(description = "编码", example = "^(PIG|PIGX)$")
    private String validateCode;

    /**
     * 是否是系统内置
     */
    @Schema(description = "是否是系统内置")
    private String systemFlag;

    /**
     * 配置类型：0-默认；1-检索；2-原文；3-报表；4-安全；5-文档；6-消息；9-其他
     */
    @Schema(description = "类型[1-检索；2-原文...]", example = "1")
    private String publicType;

}
