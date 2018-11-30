package com.fuguojie.springboot.architecture.learning.common.constants;

/**
 * <p>统一正则表达式</p>
 * CommonRegularExpression
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 10:30
 * @since JDK 1.8
 */
public interface CommonRegularExpression {
    /**
     * 手机号正则，1开头，第二位为3-9，后9位数字，共11位
     */
    String MOBILEPHONE_REP = "^1[3-9][0-9]{9}$";

    /**
     * 邮箱正则：
     * 前缀：必须以字母或数字结尾，支持_-共1-21位
     * 中间@
     * 后缀：第一段：1-20字母数字汉字；
     * 至少包一级域名，最多四级域名，域名限制2-10位字母数字汉字
     * 邮箱整体长度：6-86位
     */
    String EMAIL_REP = "^[a-zA-Z0-9_-]{0,20}[a-zA-Z0-9]@[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}([.][a-zA-Z0-9\\u4e00-\\u9fa5]{2,10})?([.][a-zA-Z0-9\\u4e00-\\u9fa5]{2,10})?([.][a-zA-Z0-9\\u4e00-\\u9fa5]{2,10})?([.][a-zA-Z0-9\\u4e00-\\u9fa5]{2,10})$";

    /**
     * 6-32为大小写字母、数字、_-@!字符
     */
    String ACCOUNT_REP = "^[a-zA-Z][\\w@!-]{5,31}$";

    /**
     * 必须包含大写字母、小写字母、数字、_-@!*四类字符，8-16位必须包含大写字母、小写字母、数字、_-@!*四类字符，8-16位
     */
    String PWD_REP = "^^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!*@\\_-])[A-Za-z\\d!*@_-]{8,16}$";

    /**
     * 大小写字母、数字、中文，1-24位
     */
    String NICKNAME ="^[A-Za-z0-9\\u4e00-\\u9fa5]{1,24}$";
}
