package com.fuguojie.springboot.architecture.learning.common.vodomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>统一的返回数据处理对象</p>
 * WrappedResponse
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 11:05
 * @since JDK 1.8
 */
@Getter
@NoArgsConstructor
public class WrappedResponse<T> extends ResultCode {
    private T payload;

    public WrappedResponse(String code, String message, T payload) {
        super(code, message);
        this.payload = payload;
    }
}
