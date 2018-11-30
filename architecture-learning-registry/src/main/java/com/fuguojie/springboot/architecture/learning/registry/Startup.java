package com.fuguojie.springboot.architecture.learning.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>注册中心启动类</p>
 * Startup
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 14:59
 * @since JDK 1.8
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class Startup {
    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
        log.info("fuguojie-registry start success.");
    }
}
