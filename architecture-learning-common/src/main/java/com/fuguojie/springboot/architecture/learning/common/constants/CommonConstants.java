package com.fuguojie.springboot.architecture.learning.common.constants;

/**
 * <p>公共常量</p>
 * CommonConstants
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:28
 * @since JDK 1.8
 */
public interface CommonConstants {

    String OS_NAME_KEY = "os.name";

    String OS_LINUX = "linux";

    String EUREKA_IP_KEY = "eureka.instance.ip-address";

    String EXP_MSG_PRE = "default message [";



    /**
     * 白名单Redis缓存key
     */
    String IPP_WHITELIST_REDISKEY = "IPP-WHITELIST";

    /**
     * 角色接口权限Redis缓存key前缀
     */
    String ROLE_PRIV_REDISKEY_PRE = "ROLE-PRIV-";
}
