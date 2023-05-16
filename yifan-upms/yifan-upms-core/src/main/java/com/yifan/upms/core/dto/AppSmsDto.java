package com.yifan.upms.core.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * AppSmsDto
 * <p>
 * 客户端请求验证码
 *
 * @author Wenzhou
 * @since 2023/5/12 10:55
 */
@Data
public class AppSmsDto {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 手机号是否存在数据库
     */
    private Boolean exist;

}
