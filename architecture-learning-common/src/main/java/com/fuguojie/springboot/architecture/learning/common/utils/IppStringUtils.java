package com.fuguojie.springboot.architecture.learning.common.utils;

/**
 * IPP 字符串工具类
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/10/17 14:19
 */
public final class IppStringUtils {

    /**
     * 判断字符串是空字符串
     * 不进行trim()操作，
     * null、"" 返回true，
     * " " " s" " s " 返回false
     *
     * @author fuguojie
     * @date   2018/10/17 15:00
     * @param str 字符串
     * @return true 是  false 不是
     */
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串不是空字符串
     * 取isEmpty反
     *
     * @author fuguojie
     * @date   2018/10/17 15:00
     * @param str 字符串
     * @return true 是  false 不是
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是空格
     * 先进行trim() 再判断
     * 
     * @author fuguojie
     * @date   2018/10/17 15:05
     * @param str 字符串
     * @return true 是  false 不是
     */
    public static boolean isBlank(String str) {
        return null == str || str.length() == 0 || "".equals(str.trim());
    }

    /**
     * 判断字符串不是空格
     * isBlank取反
     *
     * @author fuguojie
     * @date   2018/10/17 15:05
     * @param str 字符串
     * @return true 是  false 不是
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否包含空格
     * 不进行trim()
     * null、"" 返回false
     * " "、"s "、" s "、"   " 返回true
     * @author fuguojie
     * @date   2018/10/17 15:05
     * @param str 字符串
     * @return true 包含  false 不包含
     */
    public static boolean containsBlank(String str) {
        if (isNotEmpty(str)) {
            int strLen = str.length();
            for(int i = 0; i < strLen; ++i) {
                if (Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否包含空格
     * 先进行trim()
     * null、""、" s"、" s " 返回false
     * " "、" s s "、"   " 返回true
     * @author fuguojie
     * @date   2018/10/17 15:05
     * @param str 字符串
     * @return true 包含  false 不包含
     */
    public static boolean trimContainsBlank(String str) {
        if (isNotBlank(str)) {
            String strNew = str.trim();
            int strLen = strNew.length();
            for(int i = 0; i < strLen; ++i) {
                if (Character.isWhitespace(strNew.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
