package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.upms.core.entity.SysDept;

import java.util.List;

import cn.hutool.core.lang.tree.Tree;

/**
 * SysDeptService
 * <p>
 * 部门管理 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:37
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询部门树菜单
     *
     * @return 树
     */
    List<Tree<Long>> listDeptTrees();

    /**
     * 查询用户部门树
     *
     * @return List<Tree < Long>>
     */
    List<Tree<Long>> listCurrentUserDeptTrees();

    /**
     * 添加信息部门
     *
     * @param sysDept SysDept
     * @return Boolean
     */
    Boolean saveDept(SysDept sysDept);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean removeDeptById(Long id);

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);

    /**
     * 查找指定部门的子部门id列表
     *
     * @param deptId 部门id
     * @return List<Long>
     */
    List<Long> listChildDeptId(Long deptId);
}

