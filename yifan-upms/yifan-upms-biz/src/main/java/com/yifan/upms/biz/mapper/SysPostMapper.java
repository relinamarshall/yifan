package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysPost;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SysPostMapper
 * <p>
 * 岗位管理表 mapper接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:09
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 通过用户ID，查询岗位信息
     *
     * @param userId 用户id
     * @return 岗位信息
     */
    List<SysPost> listPostsByUserId(Long userId);
}