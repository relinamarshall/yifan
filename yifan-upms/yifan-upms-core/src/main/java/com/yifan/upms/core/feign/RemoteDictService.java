package com.yifan.upms.core.feign;

import com.yifan.common.core.constant.ServiceNameConstants;
import com.yifan.common.core.util.R;
import com.yifan.upms.core.entity.SysDictItem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * RemoteDictService
 *
 * @author Wenzhou
 * @since 2023/5/8 15:58
 */
@FeignClient(contextId = "remoteDictService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDictService {

    /**
     * 通过字典类型查找字典
     *
     * @param type 字典类型
     * @return 同类型字典
     */
    @GetMapping("/dict/type/{type}")
    R<List<SysDictItem>> getDictByType(@PathVariable("type") String type);
}
