package com.fuguojie.springboot.architecture.learning.common.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>配置项获取</p>
 *
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 14:09
 * @since JDK 1.8
 */
@ConfigurationProperties(prefix = "microservice")
@Getter
@Setter
public class MicroserviceProperties {
    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
}
