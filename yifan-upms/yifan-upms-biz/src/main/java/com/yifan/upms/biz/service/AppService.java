package com.yifan.upms.biz.service;

import com.yifan.common.core.util.R;
import com.yifan.upms.core.dto.AppSmsDto;

/**
 * AppService
 *
 * @author Wenzhou
 * @since 2023/5/12 10:37
 */
public interface AppService {

    /**
     * 发送手机验证码
     * @param sms phone
     * @return code
     */
    R<Boolean> sendSmsCode(AppSmsDto sms);

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码
     * @return boolean
     */
    boolean check(String phone, String code);

}