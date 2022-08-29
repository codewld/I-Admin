package pers.codewld.iadmin.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 环境 工具类
 * <p>帮助（未被Spring托管的对象）获取Spring环境中的Bean和Property
 */
@Component
public class ContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        ContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Bean
     */
    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取Bean
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 获取属性
     * @param key 属性键
     * @return 属性值
     */
    public static String getProperty(String key) {
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty(key);
        Assert.notNull(property, "属性不存在");
        return property;
    }

}