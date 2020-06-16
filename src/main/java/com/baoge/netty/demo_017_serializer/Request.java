package com.baoge.netty.demo_017_serializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/16 14:33
 */
public class Request implements Serializable {

    private String request;

    private Date requestTime;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "request='" + request + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }
}
