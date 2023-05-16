package com.yifan.upms.core.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * SysLogDto
 *
 * @author Wenzhou
 * @since 2023/5/12 10:49
 */
@Data
@Schema(description = "日志查询对象")
public class SysLogDto {
    /**
     * 查询日志类型
     */
    @Schema(description = "日志类型")
    private String type;

    /**
     * 创建时间区间 [开始时间，结束时间]
     */
    @Schema(description = "创建时间区间")
    private LocalDateTime[] crtTm;

    /**
     * 请求IP
     */
    private String remoteAddr;
}
