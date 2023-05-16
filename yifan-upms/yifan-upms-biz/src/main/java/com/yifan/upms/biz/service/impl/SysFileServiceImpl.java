package com.yifan.upms.biz.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.plugin.oss.OssProperties;
import com.pig4cloud.plugin.oss.service.OssTemplate;
import com.yifan.common.core.util.R;
import com.yifan.upms.biz.mapper.SysFileMapper;
import com.yifan.upms.biz.service.SysFileService;
import com.yifan.upms.core.entity.SysFile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * SysFileServiceImpl
 * <p>
 * 文件管理
 *
 * @author Wenzhou
 * @since 2023/5/12 12:43
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private final OssProperties ossProperties;

    private final OssTemplate ossTemplate;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return R
     */
    @Override
    public R uploadFile(MultipartFile file) {
        String fileName = IdUtil.simpleUUID() + StrPool.DOT + FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("bucketName", ossProperties.getBucketName());
        resultMap.put("fileName", fileName);
        resultMap.put("url", String.format("/admin/sys-file/%s/%s", ossProperties.getBucketName(), fileName));

        try {
            ossTemplate.putObject(ossProperties.getBucketName(), fileName, file.getContentType(),
                    file.getInputStream());
            // 文件管理数据记录,收集管理追踪文件
            fileLog(file, fileName);
        } catch (Exception e) {
            log.error("上传失败", e);
            return R.failed(e.getLocalizedMessage());
        }
        return R.ok(resultMap);
    }

    /**
     * 读取文件
     *
     * @param bucket   String
     * @param fileName String
     * @param response HttpServletResponse
     */
    @Override
    public void getFile(String bucket, String fileName, HttpServletResponse response) {
        try (S3Object s3Object = ossTemplate.getObject(bucket, fileName)) {
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常: {}", e.getLocalizedMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param id Long
     * @return Boolean
     */
    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteFile(Long id) {
        SysFile file = this.getById(id);
        ossTemplate.removeObject(ossProperties.getBucketName(), file.getFileName());
        return this.removeById(id);
    }

    /**
     * 文件管理数据记录,收集管理追踪文件
     *
     * @param file     上传文件格式
     * @param fileName 文件名
     */
    private void fileLog(MultipartFile file, String fileName) {
        SysFile sysFile = new SysFile();
        sysFile.setFileName(fileName);
        sysFile.setOriginal(file.getOriginalFilename());
        sysFile.setFileSize(file.getSize());
        sysFile.setType(FileUtil.extName(file.getOriginalFilename()));
        sysFile.setBucketName(ossProperties.getBucketName());
        this.save(sysFile);
    }

    /**
     * 默认获取文件的在线地址
     *
     * @param bucket String
     * @param fileName String
     * @return String
     */
    @Override
    public String onlineFile(String bucket, String fileName) {
        return ossTemplate.getObjectURL(bucket, fileName, Duration.of(7, ChronoUnit.DAYS));
    }

}

