package pers.codewld.iadmin.common.util;

import com.google.common.base.CaseFormat;

/**
 * 命名规则 工具类
 */
public class CaseFormatUtils {

    /**
     * 驼峰命名法转下划线命名法
     */
    public static String camelCase2UnderScoreCase(String camelCaseString) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCaseString);
    }

    /**
     * 下划线命名法转驼峰命名法
     */
    public static String underScoreCase2CamelCase(String underScoreCaseString) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underScoreCaseString);
    }
}
