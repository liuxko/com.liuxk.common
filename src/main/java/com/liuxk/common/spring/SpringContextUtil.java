package com.liuxk.common.spring;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 项目名称: 
 * 类名: SpringContextUtil
 * 描述： 获取bean的工具类，可用于在线程里面获取bean
 * 创建人: awsm
 * 创建时间: Dec 17, 2015 10:46:44 PM
 * 修改人：awsm
 * 修改时间：Dec 17, 2015 10:46:44 PM
 * 修改备注：
 * 版本：1.0
 */
public class SpringContextUtil extends PropertyPlaceholderConfigurer implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName){
        return (T) context.getBean(beanName);
    }

    public static String getMessage(String key){
        return context.getMessage(key, null, Locale.getDefault());
    }

    private static Map<String, String> map = new HashMap<String, String>() ;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
        super.processProperties(beanFactory, props) ;
        for (Object key : props.keySet()) {
            String keyStr = key.toString() ;
            String value = props.getProperty(keyStr) ;
            map.put(keyStr, value) ;
        }
    }

    public static String getProperty(String key) {
        return map.get(key);
    }

}