package com.yifan.common.xss.core;

import com.yifan.common.xss.config.XssProperties;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;

/**
 * XssCleanInterceptor
 * <p>
 * xss 处理拦截器
 *
 * @author Wenzhou
 * @since 2023/5/12 9:50
 */
@RequiredArgsConstructor
public class XssCleanInterceptor implements AsyncHandlerInterceptor {
    private final XssProperties xssProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1. 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 2. 没有开启
        if (!xssProperties.isEnabled()) {
            return true;
        }
        // 3. 处理 XssIgnore 注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        XssCleanIgnore xssCleanIgnore = AnnotationUtils.getAnnotation(handlerMethod.getMethod(), XssCleanIgnore.class);
        if (xssCleanIgnore == null) {
            XssHolder.setEnable();
        } else if (ArrayUtil.isNotEmpty(xssCleanIgnore.value())) {
            XssHolder.setEnable();
            XssHolder.setXssCleanIgnore(xssCleanIgnore);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        XssHolder.remove();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        XssHolder.remove();
    }
}
