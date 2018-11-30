package com.fuguojie.springboot.architecture.learning.common.constants;

/**
 * <p>公共头参数</p>
 * CommonHeaders
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:29
 * @since JDK 1.8
 */
public interface CommonHeaders {

    /**
     * 请求事务唯一标识，规则为TID + System.currentTimeMillis();
     */
    String X_TRANSACTION = "X-Transaction";

    /**
     * 请求客户端IP
     */
    String X_CLIENT_ADDRESS = "X-Client-Address";

    /**
     * 请求token
     */
    String X_USER_TOKEN = "X-User-Token";

    /**
     * 登录的用户ID
     */
    String X_USER_ID = "X-User-Id";

    /**
     * 登录账号
     */
    String X_USER_ACCOUNT = "X-User-Account";

    /**
     * 登录的用户的角色ID
     */
    String X_USER_ROLES = "X-User-Roles";

    /**
     * 登录的用户的机构ID
     */
    String X_USER_ORG_ID = "X-User-OrgId";
}
