package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysUserRole;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysUserRoleMapper
 * 用户角色表 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:09
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

}
