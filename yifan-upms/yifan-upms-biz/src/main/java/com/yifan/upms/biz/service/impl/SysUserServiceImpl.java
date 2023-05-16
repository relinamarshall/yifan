package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import com.yifan.common.core.constant.CacheConstants;
import com.yifan.common.core.constant.CommonConstants;
import com.yifan.common.core.constant.enums.MenuTypeEnum;
import com.yifan.common.core.exception.ErrorCodes;
import com.yifan.common.core.util.MsgUtils;
import com.yifan.common.core.util.R;
import com.yifan.upms.biz.mapper.SysDeptMapper;
import com.yifan.upms.biz.mapper.SysPostMapper;
import com.yifan.upms.biz.mapper.SysRoleMapper;
import com.yifan.upms.biz.mapper.SysUserMapper;
import com.yifan.upms.biz.mapper.SysUserPostMapper;
import com.yifan.upms.biz.mapper.SysUserRoleMapper;
import com.yifan.upms.biz.service.AppService;
import com.yifan.upms.biz.service.SysMenuService;
import com.yifan.upms.biz.service.SysUserService;
import com.yifan.upms.core.dto.UserDto;
import com.yifan.upms.core.dto.UserInfo;
import com.yifan.upms.core.entity.SysDept;
import com.yifan.upms.core.entity.SysMenu;
import com.yifan.upms.core.entity.SysPost;
import com.yifan.upms.core.entity.SysRole;
import com.yifan.upms.core.entity.SysUser;
import com.yifan.upms.core.entity.SysUserPost;
import com.yifan.upms.core.entity.SysUserRole;
import com.yifan.upms.core.util.ParamResolver;
import com.yifan.upms.core.vo.UserExcelVo;
import com.yifan.upms.core.vo.UserVo;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SysUserServiceImpl
 *
 * @author Wenzhou
 * @since 2023/5/12 12:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final AppService appService;

    private final SysRoleMapper sysRoleMapper;

    private final SysDeptMapper sysDeptMapper;

    private final SysMenuService sysMenuService;

    private final SysPostMapper sysPostMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserPostMapper sysUserPostMapper;

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        userDto.getRole().stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            return userRole;
        }).forEach(sysUserRoleMapper::insert);
        // 保存用户岗位信息
        Optional.ofNullable(userDto.getPost()).ifPresent(posts -> {
            posts.stream().map(postId -> {
                SysUserPost userPost = new SysUserPost();
                userPost.setUserId(sysUser.getUserId());
                userPost.setPostId(postId);
                return userPost;
            }).forEach(sysUserPostMapper::insert);
        });
        return Boolean.TRUE;
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUser 用户
     * @return UserInfo
     */
    @Override
    public UserInfo getUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        // 设置角色列表
        List<SysRole> roleList = sysRoleMapper.listRolesByUserId(sysUser.getUserId());
        userInfo.setRoleList(roleList);
        // 设置角色列表 （ID）
        List<Long> roleIds = roleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, Long.class));
        // 设置岗位列表
        List<SysPost> postList = sysPostMapper.listPostsByUserId(sysUser.getUserId());
        userInfo.setPostList(postList);
        // 设置权限列表（menu.permission）
        Set<String> permissions = roleIds.stream()
                .map(sysMenuService::findMenuByRoleId)
                .flatMap(Collection::stream)
                .filter(m -> MenuTypeEnum.BUTTON.getType().equals(m.getType()))
                .map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));

        return userInfo;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public IPage<UserVo> getUserWithRolePage(Page page, UserDto userDTO) {
        return baseMapper.getUserVosPage(page, userDTO);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVo getUserVoById(Long id) {
        return baseMapper.getUserVoById(id);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
    public Boolean removeUserById(SysUser sysUser) {
        sysUserRoleMapper.deleteByUserId(sysUser.getUserId());
        // 删除用户职位关系
        sysUserPostMapper.delete(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getUserId, sysUser.getUserId()));
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public R<Boolean> updateUserInfo(UserDto userDto) {
        UserVo userVO = baseMapper.getUserVoByUsername(userDto.getUsername());

        // 判断手机号是否修改,更新手机号校验验证码
        if (!StrUtil.equals(userVO.getPhone(), userDto.getPhone())) {
            if (!appService.check(userDto.getPhone(), userDto.getCode())) {
                return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_ERROR));
            }
        }

        // 修改密码逻辑
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getNewpassword1())) {
            Assert.isTrue(ENCODER.matches(userDto.getPassword(), userVO.getPassword()),
                    MsgUtils.getMessage(ErrorCodes.SYS_USER_UPDATE_PASSWORDERROR));
            sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVO.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return R.ok(this.updateById(sysUser));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public R<Boolean> updateUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdTm(LocalDateTime.now());

        if (CharSequenceUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleMapper
                .delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        sysUserPostMapper.delete(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getUserId, userDto.getUserId()));
        userDto.getPost().forEach(postId -> {
            SysUserPost userPost = new SysUserPost();
            userPost.setUserId(sysUser.getUserId());
            userPost.setPostId(postId);
            userPost.insert();
        });
        return R.ok();
    }

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    @Override
    public List<SysUser> listAncestorUsersByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));

        SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
        if (sysDept == null) {
            return null;
        }

        Long parentId = sysDept.getParentId();
        return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, parentId));
    }

    /**
     * 查询全部的用户
     *
     * @param userDTO 查询条件
     * @return list
     */
    @Override
    public List<UserExcelVo> listUser(UserDto userDTO) {
        List<UserVo> voList = baseMapper.selectVoList(userDTO);
        // 转换成execl 对象输出
        return voList.stream().map(userVO -> {
            UserExcelVo excelVO = new UserExcelVo();
            BeanUtils.copyProperties(userVO, excelVO);
            String roleNameList = userVO.getRoleList()
                    .stream()
                    .map(SysRole::getRoleName)
                    .collect(Collectors.joining(StrUtil.COMMA));
            excelVO.setRoleNameList(roleNameList);
            String postNameList = userVO.getPostList()
                    .stream()
                    .map(SysPost::getPostName)
                    .collect(Collectors.joining(StrUtil.COMMA));
            excelVO.setPostNameList(postNameList);
            return excelVO;
        }).collect(Collectors.toList());
    }

    /**
     * excel 导入用户, 插入正确的 错误的提示行号
     *
     * @param excelVOList   excel 列表数据
     * @param bindingResult 错误数据
     * @return ok fail
     */
    @Override
    public R importUser(List<UserExcelVo> excelVOList, BindingResult bindingResult) {
        // 通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();

        // 个性化校验逻辑
        List<SysUser> userList = this.list();
        List<SysDept> deptList = sysDeptMapper.selectList(Wrappers.emptyWrapper());
        List<SysRole> roleList = sysRoleMapper.selectList(Wrappers.emptyWrapper());
        List<SysPost> postList = sysPostMapper.selectList(Wrappers.emptyWrapper());

        // 执行数据插入操作 组装 UserDto
        for (UserExcelVo excel : excelVOList) {
            Set<String> errorMsg = new HashSet<>();
            // 校验用户名是否存在
            boolean existUserName = userList.stream()
                    .anyMatch(sysUser -> excel.getUsername().equals(sysUser.getUsername()));

            if (existUserName) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, excel.getUsername()));
            }

            // 判断输入的部门名称列表是否合法
            Optional<SysDept> deptOptional = deptList.stream()
                    .filter(dept -> excel.getDeptName().equals(dept.getName()))
                    .findFirst();
            if (!deptOptional.isPresent()) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_DEPT_DEPTNAME_INEXISTENCE, excel.getDeptName()));
            }

            // 判断输入的角色名称列表是否合法
            List<String> roleNameList = StrUtil.split(excel.getRoleNameList(), StrUtil.COMMA);
            List<SysRole> roleCollList = roleList.stream()
                    .filter(role -> roleNameList.stream().anyMatch(name -> role.getRoleName().equals(name)))
                    .collect(Collectors.toList());

            if (roleCollList.size() != roleNameList.size()) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_ROLE_ROLENAME_INEXISTENCE, excel.getRoleNameList()));
            }

            // 判断输入的岗位名称列表是否合法
            List<String> postNameList = StrUtil.split(excel.getPostNameList(), StrUtil.COMMA);
            List<SysPost> postCollList = postList.stream()
                    .filter(post -> postNameList.stream().anyMatch(name -> post.getPostName().equals(name)))
                    .collect(Collectors.toList());

            if (postCollList.size() != postNameList.size()) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_POST_POSTNAME_INEXISTENCE, excel.getPostNameList()));
            }

            // 数据合法情况
            if (CollUtil.isEmpty(errorMsg)) {
                insertExcelUser(excel, deptOptional, roleCollList, postCollList);
            } else {
                // 数据不合法情况
                errorMessageList.add(new ErrorMessage(excel.getLineNum(), errorMsg));
            }

        }

        if (CollUtil.isNotEmpty(errorMessageList)) {
            return R.failed(errorMessageList);
        }
        return R.ok();
    }

    @Override
    public List<Long> listUserIdByDeptIds(Set<Long> deptIds) {
        return this.listObjs(
                Wrappers.lambdaQuery(SysUser.class).select(SysUser::getUserId).in(SysUser::getDeptId, deptIds),
                Long.class::cast);
    }

    /**
     * 插入excel User
     */
    private void insertExcelUser(UserExcelVo excel, Optional<SysDept> deptOptional, List<SysRole> roleCollList,
                                 List<SysPost> postCollList) {
        UserDto userDTO = new UserDto();
        userDTO.setUsername(excel.getUsername());
        userDTO.setPhone(excel.getPhone());
        // 批量导入初始密码为手机号
        userDTO.setPassword(userDTO.getPhone());
        // 根据部门名称查询部门ID
        userDTO.setDeptId(deptOptional.get().getDeptId());
        // 根据角色名称查询角色ID
        List<Long> roleIdList = roleCollList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        userDTO.setRole(roleIdList);
        List<Long> postIdList = postCollList.stream().map(SysPost::getPostId).collect(Collectors.toList());
        userDTO.setPost(postIdList);
        // 插入用户
        this.saveUser(userDTO);
    }

    /**
     * 注册用户 赋予用户默认角色
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @Override
    public R<Boolean> registerUser(UserDto userDto) {
        // 校验验证码
        if (!appService.check(userDto.getPhone(), userDto.getCode())) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_ERROR));
        }

        // 判断用户名是否存在
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userDto.getUsername()));
        if (sysUser != null) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, userDto.getUsername()));
        }

        // 获取默认角色编码
        String defaultRole = ParamResolver.getStr("USER_DEFAULT_ROLE");
        // 默认角色
        SysRole sysRole = sysRoleMapper
                .selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, defaultRole));

        if (sysRole == null) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_PARAM_CONFIG_ERROR, "USER_DEFAULT_ROLE"));
        }

        userDto.setRole(Collections.singletonList(sysRole.getRoleId()));
        return R.ok(saveUser(userDto));
    }

}
