package com.fuguojie.springboot.architecture.learning.common.handler;

import com.fuguojie.springboot.architecture.learning.common.constants.CommonConstants;
import com.fuguojie.springboot.architecture.learning.common.constants.CommonResultCode;
import com.fuguojie.springboot.architecture.learning.common.vodomain.BusinessException;
import com.fuguojie.springboot.architecture.learning.common.vodomain.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>接口异常处理</p>
 * ControllerExceptionHandler
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 11:19
 * @since JDK 1.8
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultCode handle(Exception e) {
        // 由Spring或Bean Validation检测出的参数异常
        if (e instanceof MethodArgumentNotValidException) {
            String msg = e.getMessage();
            if (!StringUtils.isEmpty(msg) && msg.lastIndexOf(CommonConstants.EXP_MSG_PRE) > 0) {
                msg = msg.substring(msg.lastIndexOf(CommonConstants.EXP_MSG_PRE) + 17, msg.length()-3);
            }

            return CommonResultCode.PARAM_INVALID.withMessage(msg);
        } else if (e instanceof MissingServletRequestParameterException
                || e instanceof HttpMessageNotReadableException) {
            log.warn("Param invalid", e);
            return CommonResultCode.PARAM_INVALID;
        } else if (e instanceof BusinessException) {
            return ((BusinessException) e).getWrappedResponse();
        } else {
            log.error("An Exception occurs.", e);
            return CommonResultCode.SERVER_ERROR;
        }
    }
}
