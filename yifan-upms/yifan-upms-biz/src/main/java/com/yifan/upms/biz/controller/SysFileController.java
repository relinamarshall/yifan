package com.yifan.upms.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yifan.common.core.util.R;
import com.yifan.common.log.annotation.SysLog;
import com.yifan.common.security.annotation.Inner;
import com.yifan.upms.biz.service.SysFileService;
import com.yifan.upms.core.entity.SysFile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * SysFileController
 * <p>
 * 文件管理
 *
 * @author Wenzhou
 * @since 2023/5/12 11:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys-file")
@Tag(name = "文件管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysFileController {

    private final SysFileService sysFileService;

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param sysFile 文件管理
     * @return IPage<SysFile>
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R<IPage<SysFile>> getSysFilePage(Page page, SysFile sysFile) {
        return R.ok(sysFileService.page(page, Wrappers.<SysFile>lambdaQuery()
                .like(CharSequenceUtil.isNotBlank(sysFile.getFileName()), SysFile::getFileName, sysFile.getFileName())));
    }

    /**
     * 通过id删除文件管理
     *
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id删除文件管理", description = "通过id删除文件管理")
    @SysLog("删除文件管理")
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("@pms.hasPermission('sys_file_del')")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.ok(sysFileService.deleteFile(id));
    }

    /**
     * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
     *
     * @param file 资源
     * @return R(/ admin / bucketName / filename)
     */
    @PostMapping(value = "/upload")
    public R upload(@RequestPart("file") MultipartFile file) {
        return sysFileService.uploadFile(file);
    }

    /**
     * 获取文件
     *
     * @param bucket   桶名称
     * @param fileName 文件空间/名称
     * @param response HttpServletResponse
     */
    @Inner(false)
    @GetMapping("/{bucket}/{fileName}")
    public void file(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
        sysFileService.getFile(bucket, fileName, response);
    }

    /**
     * 获取本地（resources）文件
     *
     * @param fileName 文件名称
     * @param response 本地文件
     */
    @SneakyThrows
    @GetMapping("/local/{fileName}")
    public void localFile(@PathVariable String fileName, HttpServletResponse response) {
        ClassPathResource resource = new ClassPathResource("file/" + fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.copy(resource.getInputStream(), response.getOutputStream());
    }

    /**
     * 获取文件外网的访问地址
     *
     * @param bucket   String
     * @param fileName String
     * @return String
     */
    @Inner(false)
    @GetMapping("/online/{bucket}/{fileName}")
    public R<String> onlineFile(@PathVariable String bucket, @PathVariable String fileName) {
        return R.ok(sysFileService.onlineFile(bucket, fileName));
    }
}
