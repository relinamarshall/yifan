package com.yifan.common.core.constant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DictTypeEnum
 * <p>
 * 字典类型
 *
 * @author Wenzhou
 * @since 2023/5/5 20:20
 */
@Getter
@RequiredArgsConstructor
public enum DictTypeEnum {
    /**
     * 字典类型-系统内置（不可修改）
     */
    SYSTEM("1", "系统内置"),

    /**
     * 字典类型-业务类型
     */
    BIZ("0", "业务类");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String description;
}
