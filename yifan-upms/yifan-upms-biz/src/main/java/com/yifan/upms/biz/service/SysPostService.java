package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysPost;
import com.yifan.upms.core.vo.PostExcelVo;

import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * SysPostService
 * <p>
 * 岗位管理 服务类
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 导入岗位
     *
     * @param excelVOList   岗位列表
     * @param bindingResult 错误信息列表
     * @return ok fail
     */
    R importPost(List<PostExcelVo> excelVOList, BindingResult bindingResult);

    /**
     * 导出excel 表格
     *
     * @return List<PostExcelVo>
     */
    List<PostExcelVo> listPost();

}