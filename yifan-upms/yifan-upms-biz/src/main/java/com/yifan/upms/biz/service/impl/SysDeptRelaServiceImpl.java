package com.yifan.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.upms.biz.mapper.SysDeptRelaMapper;
import com.yifan.upms.biz.service.SysDeptRelaService;
import com.yifan.upms.core.entity.SysDept;
import com.yifan.upms.core.entity.SysDeptRela;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;

/**
 * SysDeptRelaServiceImpl
 * <p>
 * 服务实现类
 *
 * @author Wenzhou
 * @since 2023/5/12 11:35
 */
@Service
@RequiredArgsConstructor
public class SysDeptRelaServiceImpl extends ServiceImpl<SysDeptRelaMapper, SysDeptRela> implements SysDeptRelaService {

    private final SysDeptRelaMapper sysDeptRelationMapper;

    /**
     * 维护部门关系
     *
     * @param sysDept 部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeptRelation(SysDept sysDept) {
        // 增加部门关系表
        List<SysDeptRela> relationList = sysDeptRelationMapper.selectList(
                        Wrappers.<SysDeptRela>query().lambda().eq(SysDeptRela::getDescendant, sysDept.getParentId()))
                .stream()
                .peek(relation -> relation.setDescendant(sysDept.getDeptId()))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relationList)) {
            this.saveBatch(relationList);
        }

        // 自己也要维护到关系表中
        SysDeptRela own = new SysDeptRela();
        own.setDescendant(sysDept.getDeptId());
        own.setAncestor(sysDept.getDeptId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 通过ID删除部门关系
     *
     * @param id
     */
    @Override
    public void removeDeptRelationById(Long id) {
        baseMapper.deleteDeptRelationsById(id);
    }

    /**
     * 更新部门关系
     *
     * @param relation
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeptRelation(SysDeptRela relation) {
        baseMapper.deleteDeptRelations(relation);
        baseMapper.insertDeptRelations(relation);
    }
}

