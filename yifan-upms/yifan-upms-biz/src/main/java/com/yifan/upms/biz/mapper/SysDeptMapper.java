package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysDept;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SysDeptMapper
 * <p>
 * 部门管理 Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:04
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 关联dept——relation
     *
     * @return 数据列表
     */
    List<SysDept> listDepts();
}

