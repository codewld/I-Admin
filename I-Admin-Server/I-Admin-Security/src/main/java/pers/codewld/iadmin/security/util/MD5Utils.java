package pers.codewld.iadmin.security.util;

import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * MD5 工具类
 */
public class MD5Utils {

    /**
     * 加密
     * @param rawString 原始字符串
     * @return 加密后字符串
     */
    public static String encode(String rawString) {
        Assert.hasText(rawString, "原始字符串不得为空");
        return DigestUtils.md5DigestAsHex(rawString.getBytes());
    }

    /**
     * 判断原始字符串与加密后字符串是否匹配
     * @param rawString 原始字符串
     * @param encodedStr 加密后字符串
     * @return 是否匹配
     */
    public static boolean matches(String rawString, String encodedStr) {
        Assert.hasText(rawString, "原始字符串不得为空");
        Assert.hasText(encodedStr, "加密后字符串不得为空");
        return encodedStr.equals(DigestUtils.md5DigestAsHex(rawString.getBytes()));
    }

}
