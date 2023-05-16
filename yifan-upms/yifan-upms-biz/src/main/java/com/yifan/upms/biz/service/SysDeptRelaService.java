package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysDept;
import com.yifan.upms.core.entity.SysDeptRela;

/**
 * SysDeptRelaService
 * <p>
 * 部门关系 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:37
 */
public interface SysDeptRelaService extends IService<SysDeptRela> {

    /**
     * 新建部门关系
     *
     * @param sysDept 部门
     */
    void saveDeptRelation(SysDept sysDept);

    /**
     * 通过ID删除部门关系
     *
     * @param id Long
     */
    void removeDeptRelationById(Long id);

    /**
     * 更新部门关系
     *
     * @param relation SysDeptRela
     */
    void updateDeptRelation(SysDeptRela relation);

}
