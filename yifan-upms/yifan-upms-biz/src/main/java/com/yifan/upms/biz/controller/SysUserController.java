package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.yifan.common.core.exception.ErrorCodes;
import com.yifan.common.core.util.MsgUtils;
import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.common.security.annotation.Inner;
import com.yifan.common.security.util.SecurityUtils;
import com.yifan.common.xss.core.XssCleanIgnore;
import com.yifan.upms.biz.service.SysUserService;
import com.yifan.upms.core.dto.UserDto;
import com.yifan.upms.core.dto.UserInfo;
import com.yifan.upms.core.entity.SysUser;
import com.yifan.upms.core.vo.UserExcelVo;
import com.yifan.upms.core.vo.UserInfoVo;
import com.yifan.upms.core.vo.UserVo;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * SysUserController
 * <p>
 * 用户管理模块
 *
 * @author Wenzhou
 * @since 2023/5/12 11:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysUserController {

    private final SysUserService userService;

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    public R<UserInfoVo> info() {
        String username = SecurityUtils.getUser().getUsername();
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
        }
        UserInfo userInfo = userService.getUserInfo(user);
        UserInfoVo vo = new UserInfoVo();
        vo.setSysUser(userInfo.getSysUser());
        vo.setRoles(userInfo.getRoles());
        vo.setPermissions(userInfo.getPermissions());
        return R.ok(vo);
    }

    /**
     * 获取指定用户全部信息
     *
     * @return 用户信息
     */
    @Inner
    @GetMapping("/info/{username}")
    public R<UserInfo> info(@PathVariable String username) {
        SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, username));
        }
        return R.ok(userService.getUserInfo(user));
    }

    /**
     * 根据部门id，查询对应的用户 id 集合
     *
     * @param deptIds 部门id 集合
     * @return 用户 id 集合
     */
    @Inner
    @GetMapping("/ids")
    public R<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds) {
        return R.ok(userService.listUserIdByDeptIds(deptIds));
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id:\\d+}")
    public R<UserVo> user(@PathVariable Long id) {
        return R.ok(userService.getUserVoById(id));
    }

    /**
     * 判断用户是否存在
     *
     * @param userDto 查询条件
     * @return Boolean
     */
    @Inner(false)
    @GetMapping("/check/exist")
    public R<Boolean> isExist(UserDto userDto) {
        List<SysUser> sysUserList = userService.list(new QueryWrapper<>(userDto));
        if (CollUtil.isNotEmpty(sysUserList)) {
            return R.ok(Boolean.TRUE, MsgUtils.getMessage(ErrorCodes.SYS_USER_EXISTING));
        }
        return R.ok(Boolean.FALSE);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @SysLog("删除用户信息")
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("@pms.hasPermission('sys_user_del')")
    public R<Boolean> userDel(@PathVariable Long id) {
        SysUser sysUser = userService.getById(id);
        return R.ok(userService.removeUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @SysLog("添加用户")
    @PostMapping
    @XssCleanIgnore({"password"})
    @PreAuthorize("@pms.hasPermission('sys_user_add')")
    public R<Boolean> user(@RequestBody UserDto userDto) {
        return R.ok(userService.saveUser(userDto));
    }

    /**
     * 管理员更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @SysLog("更新用户信息")
    @PutMapping
    @XssCleanIgnore({"password"})
    @PreAuthorize("@pms.hasPermission('sys_user_edit')")
    public R<Boolean> updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDto 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R<IPage<UserVo>> getUserPage(Page page, UserDto userDto) {
        return R.ok(userService.getUserWithRolePage(page, userDto));
    }

    /**
     * 个人修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @SysLog("修改个人信息")
    @PutMapping("/edit")
    @XssCleanIgnore({"password", "newpassword1"})
    public R<Boolean> updateUserInfo(@Valid @RequestBody UserDto userDto) {
        userDto.setUsername(SecurityUtils.getUser().getUsername());
        return userService.updateUserInfo(userDto);
    }

    /**
     * listAncestorUsers
     *
     * @param username 用户名称
     * @return 上级部门用户列表
     */
    @GetMapping("/ancestor/{username}")
    public R<List<SysUser>> listAncestorUsers(@PathVariable String username) {
        return R.ok(userService.listAncestorUsersByUsername(username));
    }

    /**
     * 导出excel 表格
     *
     * @param userDto 查询条件
     * @return
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('sys_user_import_export')")
    public List<UserExcelVo> export(UserDto userDto) {
        return userService.listUser(userDto);
    }

    /**
     * 导入用户
     *
     * @param excelVOList   用户列表
     * @param bindingResult 错误信息列表
     * @return R
     */
    @PostMapping("/import")
    @PreAuthorize("@pms.hasPermission('sys_user_import_export')")
    public R importUser(@RequestExcel List<UserExcelVo> excelVOList, BindingResult bindingResult) {
        return userService.importUser(excelVOList, bindingResult);
    }

}
