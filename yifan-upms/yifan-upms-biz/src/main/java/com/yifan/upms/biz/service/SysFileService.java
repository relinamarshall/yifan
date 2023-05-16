package com.yifan.upms.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysFile;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * SysFileService
 * <p>
 * 文件管理
 *
 * @author Wenzhou
 * @since 2023/5/12 10:38
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return R
     */
    R uploadFile(MultipartFile file);

    /**
     * 读取文件
     *
     * @param bucket   桶名称
     * @param fileName 文件名称
     * @param response 输出流
     */
    void getFile(String bucket, String fileName, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @param id Long
     * @return Boolean
     */
    Boolean deleteFile(Long id);

    /**
     * 获取外网访问地址
     *
     * @param bucket   String
     * @param fileName String
     * @return String
     */
    String onlineFile(String bucket, String fileName);
}

