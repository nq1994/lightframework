package org.smart4j.smart.helper;

/**
 * Created by liangyue on 2018/1/3.
 */

import org.smart4j.smart.annotation.Controller;
import org.smart4j.smart.annotation.Service;
import org.smart4j.smart.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * 通过该ClassHelper类封装ClassUtil类
 */
public final class ClassHelper {

    /**
     * 定义类集合，用于存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有Service类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有Controller类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
        	//如果指定注释存在这个类里，则返回true；这是注解实现方式
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有bean类(包括Service、Controller等)
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET){
            if (cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}



