package com.fuguojie.springboot.architecture.learning.common.handler;

import com.google.common.collect.ImmutableList;
import com.fuguojie.springboot.architecture.learning.common.constants.CommonResultCode;
import com.fuguojie.springboot.architecture.learning.common.utils.JsonUtils;
import com.fuguojie.springboot.architecture.learning.common.vodomain.ResultCode;
import com.fuguojie.springboot.architecture.learning.common.vodomain.WrappedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * <p>接口返回数据处理</p>
 * ControllerResponseWrapper
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 11:45
 * @since JDK 1.8
 */
@RestControllerAdvice
@Slf4j
public class ControllerResponseWrapper implements ResponseBodyAdvice<Object> {
    private static final List<Class<? extends HttpMessageConverter>> PASSED_CONVERTER_TYPES =
            ImmutableList.of(ResourceHttpMessageConverter.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !PASSED_CONVERTER_TYPES.contains(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 在Controller的某个接口方法返回String时，
        // 会由StringHttpMessageConverter进行response写入，而不再是MappingJackson2HttpMessageConverter
        // 所以预先转好JSON返回
        if (body instanceof String) {
            return JsonUtils.writeValueAsString(new WrappedResponse<>(CommonResultCode.SUCCESS.getCode(), null, body));
        }
        // 如果已经包装成了ResponseWrapper，例如ExceptionHandler处理的，则不再处理
        else if (body instanceof ResultCode) {
            return body;
        } else {
            return new WrappedResponse<>(CommonResultCode.SUCCESS.getCode(), null, body);
        }
    }
}
