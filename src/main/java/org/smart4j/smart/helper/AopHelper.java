package org.smart4j.smart.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.smart.annotation.Aspect;
import org.smart4j.smart.proxy.AspectProxy;
import org.smart4j.smart.proxy.Proxy;
import org.smart4j.smart.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by liangyue on 2018/1/7.
 */

/**
 * 方法拦截助手类
 */
public final class AopHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

	/**
	 * 通过一个静态块来初始化AOP框架
	 */
	static {
		try {
			Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				System.out.println("targetClass: "+targetClass+" proxy: "+proxy);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			LOGGER.error("aop failure", e);
		}
	}

	/**
	 * 获取一个aspect注解的value（该value是一个特定注解，通过它得到所有使用该特定注解的所有类）
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if (annotation != null && !annotation.equals(Aspect.class)){
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}

	/**
	 * 获取切面逻辑（代理）和目标作用类之间的映射关系
	 * 代理类就是指切面类，一个代理类可以有多个目标类
	 * 代理类需要扩展AspectProxy抽象类，还需要Aspect注解
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> proxyClass : proxyClassSet){
			if (proxyClass.isAnnotationPresent(Aspect.class)){
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
		return proxyMap;
	}

	/**
	 * 与createProxyMap逻辑相反，不再是一个切面逻辑对应一组目标类，而是一个类对应一组切面逻辑
	 * 有多个代理类，每个代理类，有一个目标类集合
	 * 所以对于每个目标类，需要一个代理对象列表
	 * @param proxyMap
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
		System.out.println("*****"+proxyMap+"*****");
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet){
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(targetClass)){
					targetMap.get(targetClass).add(proxy);
				}
				else {
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
