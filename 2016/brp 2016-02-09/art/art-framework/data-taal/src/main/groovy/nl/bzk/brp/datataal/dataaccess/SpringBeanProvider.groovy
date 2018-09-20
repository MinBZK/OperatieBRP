package nl.bzk.brp.datataal.dataaccess

import groovy.transform.TypeChecked
import org.springframework.context.ApplicationContext

/**
 * Klasse die toegang biedt tot de springcontext.
 */
@TypeChecked
public final class SpringBeanProvider {

    private static ThreadLocal<ApplicationContext> threadLocal = new ThreadLocal<>()

    public static void setContext(ApplicationContext applicationContext) {
        threadLocal.set(applicationContext);
    }

    public static ApplicationContext getContext() {
        return threadLocal.get();
    }

    /**
     * util klasse heeft geen publieke constructor nodig.
     */
    private SpringBeanProvider() {
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     * @param requiredType type the bean must match; can be an interface or superclass.
     * {@code null} is disallowed.
     * <p>This method goes into {@link org.springframework.beans.factory.ListableBeanFactory} by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type. For more extensive retrieval operations across sets of beans,
     * use {@link org.springframework.beans.factory.ListableBeanFactory} and/or {@link org.springframework.beans.factory.BeanFactoryUtils}.
     * @return an instance of the single bean matching the required type
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if no bean of the given type was found
     * @throws org.springframework.beans.factory.NoUniqueBeanDefinitionException if more than one bean of the given type was found
     * @since 3.0
     * @see org.springframework.beans.factory.ListableBeanFactory
     */
    public static <T> T getBean(Class<T> clazz) {
        return threadLocal.get().getBean(clazz)
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a Spring BeanFactory to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean definition
     * with the specified name
     * @throws org.springframework.beans.BeansException if the bean could not be obtained
     */
    public static Object getBean(String beanName) {
        return threadLocal.get().getBean(beanName)
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
     * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
     * required type. This means that ClassCastException can't be thrown on casting
     * the result correctly, as can happen with {@link #getBean(String)}.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @param requiredType type the bean must match. Can be an interface or superclass
     * of the actual class, or {@code null} for any match. For example, if the value
     * is {@code Object.class}, this method will succeed whatever the class of the
     * returned instance.
     * @return an instance of the bean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no such bean definition
     * @throws org.springframework.beans.factory.BeanNotOfRequiredTypeException if the bean is not of the required type
     * @throws org.springframework.beans.BeansException if the bean could not be created
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return threadLocal.get().getBean(name, requiredType)
    }

}
