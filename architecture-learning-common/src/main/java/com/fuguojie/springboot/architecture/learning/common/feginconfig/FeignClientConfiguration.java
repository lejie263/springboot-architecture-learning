package com.fuguojie.springboot.architecture.learning.common.feginconfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fuguojie.springboot.architecture.learning.common.utils.JsonUtils;
import com.fuguojie.springboot.architecture.learning.common.vodomain.BusinessException;
import com.fuguojie.springboot.architecture.learning.common.constants.CommonResultCode;
import com.fuguojie.springboot.architecture.learning.common.vodomain.WrappedResponse;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * FeignClient定制<br/>
 * <p>Controller方法返回值会被包装成WrappedResponse返回给客户端，FeignClient作为客户端需要将响应解析出payload返回给调用方</p>
 * FeignClientConfiguration
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 13:25
 * @since JDK 1.8
 */
@Configuration
public class FeignClientConfiguration {
    @Bean
    public Decoder feignDecoder() {
        return (response, type) -> {
            TypeReference typeReference = new TypeReference<Object>() {
                @Override
                public Type getType() {
                    return new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() {
                            return new Type[] {type};
                        }

                        @Override
                        public Type getRawType() {
                            return WrappedResponse.class;
                        }

                        @Override
                        public Type getOwnerType() {
                            return null;
                        }
                    };
                }
            };

            WrappedResponse<?> wrappedResponse = JsonUtils.getObjectMapper().readValue(
                    response.body().asInputStream(), typeReference);
            if (!CommonResultCode.SUCCESS.codeEquals(wrappedResponse.getCode())) {
                throw BusinessException.from(wrappedResponse.getCode(), wrappedResponse.getMessage());
            }
            return wrappedResponse.getPayload();
        };
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
}
