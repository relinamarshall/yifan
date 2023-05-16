package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysRole;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SysRoleMapper
 * <p>
 * 角色表
 *
 * @author Wenzhou
 * @since 2023/5/12 10:09
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId Long
     * @return List<SysRole>
     */
    List<SysRole> listRolesByUserId(Long userId);
}
