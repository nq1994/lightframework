package org.smart4j.smart.proxy;

/**
 * Created by liangyue on 2018/1/4.
 */

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 * 所谓链式代理，就是讲多个代理通过一条链子串起来，一个一个去执行，执行顺序取决于添加到链上的先后顺序。
 */
public class ProxyChain {
	//	目标类
	private final Class<?> targetClass;

	//	目标对象
	private final Object targetObject;

	//	目标方法
	private final Method targetMethod;

	//	方法代理，CGLib提供的方法代理对象
	private final MethodProxy methodProxy;

	//	方法参数
	private final Object[] methodParams;

	private List<Proxy> proxyList = new ArrayList<Proxy>();

	//代理对象计数器
	private int proxyIndex = 0;

	public ProxyChain(Class<?> targetClass, Object targetObject,
                      Method targetMethod, MethodProxy methodProxy,
                      Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	public Object doProxyChain() throws Throwable{
		Object methodResult;
		//若计数器未达到proxyList的上限，则从proxyList中取出相应的Proxy对象，并调用其doProxy方法
		//在Proxy的接口实现中会提供相应的横切逻辑，并调用doProxyChain方法，（即随后将再次调用当前对象
		//的doProxyChain方法），直到达到上限为止，最后调用methodProxy的invokeSuper方法，执行目标对象
		//的业务逻辑。
		if (proxyIndex < proxyList.size()){
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		}
		else {
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
}
