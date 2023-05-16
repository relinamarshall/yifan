package com.yifan.upms.biz.controller;

import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.common.security.annotation.Inner;
import com.yifan.upms.biz.service.SysUserService;
import com.yifan.upms.core.dto.UserDto;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * SysRegisterController
 * <p>
 * 客户端注册功能 register.user = false
 *
 * @author Wenzhou
 * @since 2023/5/12 11:23
 */
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "register.user", matchIfMissing = true)
public class SysRegisterController {

    private final SysUserService userService;

    /**
     * 注册用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @Inner(value = false)
    @SysLog("注册用户")
    @PostMapping("/user")
    public R<Boolean> registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

}

