package com.fuguojie.springboot.architecture.learning.common.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fuguojie.springboot.architecture.learning.common.constants.CommonConstants;
import com.fuguojie.springboot.architecture.learning.common.handler.ControllerResponseWrapper;
import com.fuguojie.springboot.architecture.learning.common.utils.JsonUtils;
import com.fuguojie.springboot.architecture.learning.common.handler.ControllerExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * <p>自动配置</p>
 *
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 14:09
 * @since JDK 1.8
 */
@Configuration
@EnableConfigurationProperties(MicroserviceProperties.class)
@Slf4j
@AutoConfigureOrder
public class MicroserviceAutoConfiguration {
    @Autowired
    private MicroserviceProperties microserviceProperties;

    @Bean
    public ControllerExceptionHandler controllerExceptionHandler() {
        return new ControllerExceptionHandler();
    }

    @Bean
    public ControllerResponseWrapper controllerResponseWrapper() {
        return new ControllerResponseWrapper();
    }

    @Bean
    public CommandLineRunner disableLog4j2ConsoleAppender() {
        return args -> {
            if (StringUtils.startsWithIgnoreCase(System.getProperty(CommonConstants.OS_NAME_KEY), CommonConstants.OS_LINUX)) {
                LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
                org.apache.logging.log4j.core.config.Configuration configuration = loggerContext.getConfiguration();
                configuration.getLoggers().forEach((key, loggerConfig) ->
                        loggerConfig.removeAppender("Console"));
                configuration.getRootLogger().removeAppender("Console");
            }
        };
    }

    @Bean
    public SimpleModule jacksonDateConfigurer() {
        return JacksonDateConfigurer.configure(microserviceProperties.getDateTimePattern());
    }

    @Bean
    public JsonUtils jsonUtils(ObjectMapper objectMapper) {
        return new JsonUtils(objectMapper);
    }

    /**
     * https://github.com/spring-cloud/spring-cloud-netflix/issues/466
     */
    @Bean
    @ConditionalOnClass(name = "feign.Feign")
    public WebMvcRegistrations feignWebRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected boolean isHandler(Class<?> beanType) {
                        return super.isHandler(beanType) &&
                                !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
                    }
                };
            }
        };
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> afterApplicationStart() {
        return event -> {
            ApplicationContext applicationContext = event.getApplicationContext();
            logEurekaClientIp(applicationContext);
            checkRedisConnectionStatus(applicationContext);
            checkDataSourceConnectionStatus(applicationContext);
        };
    }

    private void checkRedisConnectionStatus(ApplicationContext applicationContext) {
        try {
            Class.forName("org.springframework.data.redis.core.StringRedisTemplate");
            if (MapUtils.isNotEmpty(applicationContext.getBeansOfType(StringRedisTemplate.class))) {
                applicationContext.getBean(StringRedisTemplate.class).opsForValue().get("a");
                log.info("Redis checked.");
            }
        } catch (ClassNotFoundException ex) {
            // 应用未引入Redis
        }
    }

    private void checkDataSourceConnectionStatus(ApplicationContext applicationContext) {
        try {
            Class.forName("javax.sql.DataSource");
            if (MapUtils.isNotEmpty(applicationContext.getBeansOfType(DataSource.class))) {
                try {
                    applicationContext.getBean(DataSource.class).getConnection().close();
                } catch (SQLException e) {
                    log.error("DataSource check failed.", e);
                    throw new RuntimeException(e);
                }
                log.info("DataSource checked.");
            }
        } catch (ClassNotFoundException ex) {
            // 应用未引入DataSource
        }
    }

    private void logEurekaClientIp(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment();
        if (environment.containsProperty(CommonConstants.EUREKA_IP_KEY)) {
            log.info("Eureka instance ip address = {}", environment.getProperty(CommonConstants.EUREKA_IP_KEY));
        }
    }
}
