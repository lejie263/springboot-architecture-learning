package com.fuguojie.springboot.architecture.learning.common.vodomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * <p>统一的自定义异常</p>
 * BusinessException
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:59
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    @Delegate
    private WrappedResponse<Object> wrappedResponse;

    public static BusinessException from(String code, String message) {
        return new BusinessException(new WrappedResponse<>(code, message, null));
    }

    public static BusinessException from(ResultCode resultCode) {
        return new BusinessException(new WrappedResponse<>(resultCode.getCode(), resultCode.getMessage(), null));
    }

    public static BusinessException from(ResultCode resultCode, String message) {
        return new BusinessException(new WrappedResponse<>(resultCode.getCode(), message, null));
    }

    public static BusinessException from(String code, String message, Object payload) {
        return new BusinessException(new WrappedResponse<>(code, message, payload));
    }

    public static BusinessException from(ResultCode resultCode, Object payload) {
        return new BusinessException(new WrappedResponse<>(resultCode.getCode(), resultCode.getMessage(), payload));
    }

    public static BusinessException from(ResultCode resultCode, String message, Object payload) {
        return new BusinessException(new WrappedResponse<>(resultCode.getCode(), message, payload));
    }

    @Override
    public synchronized BusinessException fillInStackTrace() {
        // 此种异常原因明确，不打印日志，所以无需填充堆栈（耗时操作）
        return this;
    }
}
