package com.can.easyquiz.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 谨慎使用：仅在无法通过常规依赖注入获取 Bean 时使用（如工具类、静态方法）
 *
 * 推荐优先级：
 * 1. 依赖注入 (@Autowired)
 * 2. @Configuration 类中的 @Bean 方法
 * 3. 本工具类（最后手段）
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    // 私有构造防止手动实例化
    private ApplicationContextProvider() {
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        // 使用内存屏障保证线程可见性
        synchronized (ApplicationContextProvider.class) {
            if (ApplicationContextProvider.context == null) {
                ApplicationContextProvider.context = applicationContext;
            }
        }
    }

    /**
     * 类型安全方式获取 Bean（推荐）
     * @param beanType 目标 Bean 的 Class 对象
     * @return 匹配类型的 Bean 实例
     * @throws IllegalStateException 如果上下文未初始化或找不到 Bean
     */
    @NonNull
    public static <T> T getBean(@NonNull Class<T> beanType) {
        assertContextReady();
        return context.getBean(beanType);
    }

    /**
     * 通过名称获取 Bean（谨慎使用）
     * @param beanName Bean 的名称
     * @param beanType 期望的类型（用于运行时类型检查）
     * @return 指定名称且类型匹配的 Bean
     * @throws IllegalStateException 如果上下文未初始化、找不到 Bean 或类型不匹配
     */
    @NonNull
    public static <T> T getBean(@NonNull String beanName, @NonNull Class<T> beanType) {
        assertContextReady();
        Object bean = context.getBean(beanName);
        if (!beanType.isInstance(bean)) {
            throw new BeanTypeMismatchException(
                    "Bean named '" + beanName + "' is not of expected type. " +
                            "Actual: " + bean.getClass().getName() + ", Expected: " + beanType.getName()
            );
        }
        return beanType.cast(bean);
    }

    /**
     * 自定义异常：Bean 类型不匹配
     */
    public static class BeanTypeMismatchException extends RuntimeException {
        public BeanTypeMismatchException(String message) {
            super(message);
        }
    }

    // 上下文状态检查
    private static void assertContextReady() {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext 未初始化！请确保：\n" +
                    "1. 此类仅用于 Spring 管理的 Bean\n" +
                    "2. 不在静态初始化块或 @PostConstruct 方法中过早调用");
        }
    }
}