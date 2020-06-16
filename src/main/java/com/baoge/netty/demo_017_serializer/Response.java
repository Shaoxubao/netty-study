package com.baoge.netty.demo_017_serializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/16 14:34
 */
public class Response implements Serializable {

    private String response;

    private Date responseTime;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }
}
