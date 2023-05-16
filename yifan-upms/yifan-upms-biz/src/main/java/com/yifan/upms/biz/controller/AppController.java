package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yifan.common.core.exception.ErrorCodes;
import com.yifan.common.core.util.MsgUtils;
import com.yifan.common.core.util.R;
import com.yifan.common.security.annotation.Inner;
import com.yifan.upms.biz.service.AppService;
import com.yifan.upms.biz.service.SysUserService;
import com.yifan.upms.core.dto.AppSmsDto;
import com.yifan.upms.core.dto.UserInfo;
import com.yifan.upms.core.entity.SysUser;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * AppController
 *
 * @author Wenzhou
 * @since 2023/5/12 10:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app")
@Tag(name = "移动端登录模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AppController {

    private final AppService appService;

    private final SysUserService userService;

    /**
     * 发送手机验证码
     *
     * @param sms 请求手机对象
     * @return code
     */
    @Inner(value = false)
    @PostMapping("/sms")
    public R<Boolean> sendSmsCode(@Valid @RequestBody AppSmsDto sms) {
        return appService.sendSmsCode(sms);
    }

    /**
     * 获取指定用户全部信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{phone}")
    public R<UserInfo> infoByMobile(@PathVariable String phone) {
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, phone));
        if (user == null) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, phone));
        }
        return R.ok(userService.getUserInfo(user));
    }
}

