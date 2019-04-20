package org.smart4j.smart.proxy;

/**
 * Created by liangyue on 2018/1/4.
 */

/**
 * 代理接口
 */
public interface Proxy {
	/**
	 * 执行链式代理
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
