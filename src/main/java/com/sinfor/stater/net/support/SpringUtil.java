package com.sinfor.stater.net.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengwen
 * @date 10:50 2022/4/29
 * @description Spring工具
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * @param
     * @return
     * @author fengwen
     * @date 10:49 2022/4/29
     * @description 获取applicationContext
     **/
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param
     * @return
     * @author fengwen
     * @date 10:49 2022/4/29
     * @description 通过name获取 Bean.
     **/
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @param
     * @return T
     * @author fengwen
     * @date 10:49 2022/4/29
     * @description 通过class获取Bean.
     **/
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * @param
     * @return T
     * @author fengwen
     * @date 10:50 2022/4/29
     * @description 通过name, 以及Clazz返回指定的Bean
     **/
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * @param
     * @return java.util.Map<java.lang.String, T>
     * @author fengwen
     * @date 10:50 2022/4/29
     * @description 通过Class返回指定的BeanMap
     **/
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

}