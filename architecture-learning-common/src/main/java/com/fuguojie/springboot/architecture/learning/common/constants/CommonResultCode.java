package com.fuguojie.springboot.architecture.learning.common.constants;

import com.fuguojie.springboot.architecture.learning.common.vodomain.ResultCode;

/**
 * <p>通用结果码</p>
 * CommonResultCode
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:30
 * @since JDK 1.8
 */
public interface CommonResultCode {
    String SUCCESS_CODE = "00000000";
    String PARAM_INVALID_CODE = "00001000";
    String PERMISSION_DENIED_CODE = "00002000";
    String SERVER_ERROR_CODE = "00002001";
    String RESOURCE_NOT_FOUND_CODE = "00002002";
    String TOKEN_INVALID_CODE = "00002000";
    String TOKEN_EXPIRED_CODE = "00002001";
    String TOKEN_ABSENT_CODE = "00002002";

    /**
     * 全局业务处理成功码
     */
    ResultCode SUCCESS = new ResultCode(SUCCESS_CODE, "成功");

    /**
     * 全局业务参数校验错误码
     */
    ResultCode PARAM_INVALID = new ResultCode(PARAM_INVALID_CODE, "参数非法");

    /**
     * 全局无权限码
     */
    ResultCode PERMISSION_DENIED = new ResultCode(PERMISSION_DENIED_CODE, "无权限");

    /**
     * 全局系统调用过程，抛出的非业务定制异常
     */
    ResultCode SERVER_ERROR = new ResultCode(SERVER_ERROR_CODE, "系统未知异常");

    /**
     * 全局系统调用过程，抛出的非资源不存在或访问异常
     */
    ResultCode RESOURCE_NOT_FOUND = new ResultCode(RESOURCE_NOT_FOUND_CODE, "资源调用失败");

    /**
     * 如果用户已经登录，传递的token与用户对应的token不一致，认为是非法
     */
    ResultCode TOKEN_INVALID = new ResultCode(TOKEN_INVALID_CODE, "非法token");

    /**
     * 如果用户已经登录，传递的token与用户对应的token一致，但是已经过期，认为是过期，而不是非法
     */
    ResultCode TOKEN_EXPIRED = new ResultCode(TOKEN_EXPIRED_CODE, "token已过期");

    /**
     * 只判断token是否在redis中存在与否
     */
    ResultCode TOKEN_ABSENT = new ResultCode(TOKEN_ABSENT_CODE, "token不存在");
}
