package com.baoge.netty.demo_019_codec_chinese;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author shaoxubao
 * @Date 2020/7/14 14:24
 */

@Data
public class ChineseMessage implements Serializable {

    private long id;
    private String message;

}
