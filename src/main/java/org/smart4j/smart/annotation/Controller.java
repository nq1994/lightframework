package org.smart4j.smart.annotation;

/**
 * Created by liangyue on 2018/1/3.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制器注解
 * @Target(ElementType.TYPE)
 * 作用目标是：接口、类、枚举、注解
 * @Retention(RetentionPolicy.RUNTIME)
 * 注解会在class字节码文件中存在，在运行时可以通过反射得到
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
