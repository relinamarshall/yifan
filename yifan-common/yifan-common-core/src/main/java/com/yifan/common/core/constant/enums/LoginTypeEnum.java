package com.yifan.common.core.constant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * LoginTypeEnum
 * <p>
 * 社交登录类型
 *
 * @author Wenzhou
 * @since 2023/5/5 20:21
 */
@Getter
@RequiredArgsConstructor
public enum LoginTypeEnum {
    /**
     * 账号密码登录
     */
    PWD("PWD", "账号密码登录"),

    /**
     * 验证码登录
     */
    SMS("SMS", "验证码登录");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}
