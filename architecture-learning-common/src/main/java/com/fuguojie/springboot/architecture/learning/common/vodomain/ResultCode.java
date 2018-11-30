package com.fuguojie.springboot.architecture.learning.common.vodomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * <p>业务结果码模型</p>
 * ResultCode
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:43
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultCode {
    /**
     * 结果吗
     */
    protected String code;

    /**
     * 结果吗对应消息
     */
    protected String message;

    public boolean codeEquals(String code) {
        return Objects.equals(this.code, code);
    }

    public ResultCode withMessage(String message) {
        return new ResultCode(this.code, message);
    }
}
