package com.yifan.upms.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.upms.core.entity.SysDeptRela;

import org.apache.ibatis.annotations.Mapper;

/**
 * SysDeptRelaMapper
 * <p>
 * Mapper 接口
 *
 * @author Wenzhou
 * @since 2023/5/12 10:05
 */
@Mapper
public interface SysDeptRelaMapper extends BaseMapper<SysDeptRela> {

    /**
     * 删除部门节点关系
     *
     * @param deptRelation 待删除的某一个部门节点
     */
    void deleteDeptRelations(SysDeptRela deptRelation);

    /**
     * 删除部门节点关系,同时删除所有关联此部门子节点的部门关系
     *
     * @param id 待删除的部门节点ID
     */
    void deleteDeptRelationsById(Long id);

    /**
     * 新增部门节点关系
     *
     * @param deptRelation 待新增的部门节点关系
     */
    void insertDeptRelations(SysDeptRela deptRelation);
}

