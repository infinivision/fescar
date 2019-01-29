package com.alibaba.fescar.core.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Codec Helper
 *
 * @author fagongzi(zhangxu19830126 @ gmail.com)
 */
public class CodecHelper {
    public static final Charset UTF8 = Charset.forName("utf-8");

    private CodecHelper() {

    }

    public static void write(ByteBuffer buf, String value) {
        if (null != value) {
            byte[] bs = value.getBytes(UTF8);
            buf.putShort((short) bs.length);
            if (bs.length > 0) {
                buf.put(bs);
            }
        } else {
            buf.putShort((short) 0);
        }
    }

    public static void write(ByteBuffer buf, long value) {
        buf.putLong(value);
    }

    public static String readString(ByteBuf in) {
        byte[] value = new byte[in.readShort()];
        in.readBytes(value);
        return new String(value, UTF8);
    }

    public static String readString(ByteBuffer buf) {
        byte[] value = new byte[buf.getShort()];
        buf.get(value);
        return new String(value, UTF8);
    }

    public static long readLong(ByteBuf in) {
        return in.readLong();
    }

    public static long readLong(ByteBuffer buf) {
        return buf.getLong();
    }
}
