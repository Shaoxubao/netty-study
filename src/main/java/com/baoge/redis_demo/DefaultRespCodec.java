package com.baoge.redis_demo;

import com.baoge.redis_demo.constant.ReplyType;
import com.baoge.redis_demo.constant.RespConstants;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;

import java.util.Map;


/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:49
 */
public enum DefaultRespCodec implements RespCodec {

    X;

    static final Map<ReplyType, RespDecoder> DECODERS = Maps.newConcurrentMap();
    private static final RespDecoder DEFAULT_DECODER = new DefaultRespDecoder();

    static {
        DECODERS.put(ReplyType.SIMPLE_STRING, new RespSimpleStringDecoder());
        DECODERS.put(ReplyType.ERROR, new RespErrorDecoder());
        DECODERS.put(ReplyType.INTEGER, new RespIntegerDecoder());
        DECODERS.put(ReplyType.BULK_STRING, new RespBulkStringDecoder());
        DECODERS.put(ReplyType.RESP_ARRAY, new RespArrayDecoder());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <IN, OUT> OUT decode(ByteBuf buffer) {
        return (OUT) DECODERS.getOrDefault(determineReplyType(buffer), DEFAULT_DECODER).decode(buffer);
    }

    private ReplyType determineReplyType(ByteBuf buffer) {
        byte firstByte = buffer.readByte();
        ReplyType replyType;
        switch (firstByte) {
            case RespConstants.PLUS_BYTE:
                replyType = ReplyType.SIMPLE_STRING;
                break;
            case RespConstants.MINUS_BYTE:
                replyType = ReplyType.ERROR;
                break;
            case RespConstants.COLON_BYTE:
                replyType = ReplyType.INTEGER;
                break;
            case RespConstants.DOLLAR_BYTE:
                replyType = ReplyType.BULK_STRING;
                break;
            case RespConstants.ASTERISK_BYTE:
                replyType = ReplyType.RESP_ARRAY;
                break;
            default: {
                throw new IllegalArgumentException("first byte:" + firstByte);
            }
        }
        return replyType;
    }

    @Override
    public <IN, OUT> ByteBuf encode(IN in) {
        // TODO
        throw new UnsupportedOperationException("encode");
    }
}
