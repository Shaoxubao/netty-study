package com.baoge.protocol_custom.entity;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:12
 */

@Data
public class ProtocolConstant {

    public static final int MAGIC_NUMBER = 10086;

    public static final int VERSION = 1;

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
}
