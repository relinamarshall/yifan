package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.common.security.util.SecurityUtils;
import com.yifan.upms.biz.mapper.SysDeptMapper;
import com.yifan.upms.biz.service.SysDeptRelaService;
import com.yifan.upms.biz.service.SysDeptService;
import com.yifan.upms.core.entity.SysDept;
import com.yifan.upms.core.entity.SysDeptRela;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import lombok.RequiredArgsConstructor;

/**
 * SysDeptServiceImpl
 * <p>
 * 部门管理 服务实现类
 *
 * @author Wenzhou
 * @since 2023/5/12 11:36
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptRelaService sysDeptRelationService;

    /**
     * 添加信息部门
     *
     * @param dept 部门
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDept(SysDept dept) {
        this.save(dept);
        sysDeptRelationService.saveDeptRelation(dept);
        return Boolean.TRUE;
    }

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeDeptById(Long id) {
        // 级联删除部门
        List<Long> idList = sysDeptRelationService
                .list(Wrappers.<SysDeptRela>query().lambda().eq(SysDeptRela::getAncestor, id))
                .stream()
                .map(SysDeptRela::getDescendant)
                .collect(Collectors.toList());

        if (CollUtil.isNotEmpty(idList)) {
            this.removeByIds(idList);
        }

        // 删除部门级联关系
        sysDeptRelationService.removeDeptRelationById(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDeptById(SysDept sysDept) {
        // 更新部门状态
        this.updateById(sysDept);
        // 更新部门关系
        SysDeptRela relation = new SysDeptRela();
        relation.setAncestor(sysDept.getParentId());
        relation.setDescendant(sysDept.getDeptId());
        sysDeptRelationService.updateDeptRelation(relation);
        return Boolean.TRUE;
    }

    /**
     * listChildDeptId
     *
     * @param deptId 部门id
     * @return List<Long>
     */
    @Override
    public List<Long> listChildDeptId(Long deptId) {
        List<SysDeptRela> deptRelations = sysDeptRelationService.list(Wrappers.<SysDeptRela>lambdaQuery()
                .eq(SysDeptRela::getAncestor, deptId)
                .ne(SysDeptRela::getDescendant, deptId));
        if (CollUtil.isNotEmpty(deptRelations)) {
            return deptRelations.stream().map(SysDeptRela::getDescendant).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 查询全部部门树
     *
     * @return 树
     */
    @Override
    public List<Tree<Long>> listDeptTrees() {
        return getDeptTree(this.list(Wrappers.emptyWrapper()), 0L);
    }

    /**
     * 查询用户部门树
     *
     * @return
     */
    @Override
    public List<Tree<Long>> listCurrentUserDeptTrees() {
        Long deptId = SecurityUtils.getUser().getDeptId();
        List<Long> descendantIdList = sysDeptRelationService
                .list(Wrappers.<SysDeptRela>query().lambda().eq(SysDeptRela::getAncestor, deptId))
                .stream()
                .map(SysDeptRela::getDescendant)
                .collect(Collectors.toList());

        List<SysDept> deptList = baseMapper.selectBatchIds(descendantIdList);
        Optional<SysDept> dept = deptList.stream().filter(item -> item.getDeptId().intValue() == deptId).findFirst();
        return getDeptTree(deptList, dept.isPresent() ? dept.get().getParentId() : 0L);
    }

    /**
     * 构建部门树
     *
     * @param depts    部门
     * @param parentId 父级id
     * @return List<Tree < Long>>
     */
    private List<Tree<Long>> getDeptTree(List<SysDept> depts, Long parentId) {
        List<TreeNode<Long>> collect = depts.stream()
                .filter(dept -> dept.getDeptId().intValue() != dept.getParentId())
                .sorted(Comparator.comparingInt(SysDept::getSortOrder))
                .map(dept -> {
                    TreeNode<Long> treeNode = new TreeNode();
                    treeNode.setId(dept.getDeptId());
                    treeNode.setParentId(dept.getParentId());
                    treeNode.setName(dept.getName());
                    treeNode.setWeight(dept.getSortOrder());
                    // 扩展属性
                    Map<String, Object> extra = new HashMap<>(2);
                    extra.put("createTime", dept.getCrtTm());
                    treeNode.setExtra(extra);
                    return treeNode;
                })
                .collect(Collectors.toList());

        return TreeUtil.build(collect, parentId);
    }
}

