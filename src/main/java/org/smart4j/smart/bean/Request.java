package org.smart4j.smart.bean;

/**
 * Created by liangyue on 2018/1/3.
 */

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 封装请求信息
 */
public class Request {
    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径
     */
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestMethod='" + requestMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                '}';
    }
}
