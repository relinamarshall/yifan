package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysRole;
import com.yifan.upms.core.vo.RoleExcelVo;

import org.springframework.validation.BindingResult;

import java.util.List;


/**
 * SysRoleService
 * <p>
 * 角色表 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:39
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过角色ID，删除角色
     *
     * @param id Long
     * @return Boolean
     */
    Boolean removeRoleById(Long id);

    /**
     * 导入角色
     *
     * @param excelVOList   角色列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    R importRole(List<RoleExcelVo> excelVOList, BindingResult bindingResult);

    /**
     * 查询全部的角色
     *
     * @return list
     */
    List<RoleExcelVo> listRole();

}
