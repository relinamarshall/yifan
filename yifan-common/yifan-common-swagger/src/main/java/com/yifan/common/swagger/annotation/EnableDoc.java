package com.yifan.common.swagger.annotation;

import com.yifan.common.swagger.config.SwaggerAutoConfiguration;
import com.yifan.common.swagger.support.SwaggerProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableDoc
 * <p>
 * 开启 pig spring doc
 * <p>
 * http://192.168.139.233:9999/swagger-ui.html
 * <p>
 * http://192.168.139.233:9999/admin/v3/api-docs
 * <p>
 * username: admin
 * <p>
 * password:  relyxgMJ   # v3.4.2+ 以后输入密文
 * <p>
 * password:  123456     #  低版本输入 123456
 * <p>
 * client_id: test
 * <p>
 * client_secret:   test
 *
 * @author Wenzhou
 * @since 2023/5/5 19:04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({SwaggerAutoConfiguration.class})
public @interface EnableDoc {
}
