package io.github.jerryofouc.chapter4;

import io.netty.buffer.*;
import io.netty.channel.ChannelOutboundHandler;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 功能介绍：
 *
 * @author zhangxiaojie
 *         Date:  9/27/14
 *         Time: 15:14
 */
public class ByteBufStudy {
    public static void main(String args[]){
//        String ss = "中国人好";
//        ByteBuf byteBuf = Unpooled.wrappedBuffer(ss.getBytes(Charset.forName("UTF-8")));
//        System.out.println(byteBuf.toString());
//        if(byteBuf.hasArray()){
//            byte[] array = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(array);
//            System.out.println(byteBuf);
//            System.out.println(new String(array,Charset.forName("UTF-8")));
//            byteBuf.discardReadBytes();
//            System.out.println(byteBuf);
//            byte[] array11 = "中国人".getBytes();
//            byteBuf.writeBytes(array11);
//            System.out.println(byteBuf);
//        }

//        Charset utf8 = Charset.forName("UTF-8");
//        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
//        ByteBuf sliced = buf.slice(0, 14);
//        System.out.println(sliced.toString(utf8));
//        buf.setByte(0, (byte) 'J');
//        System.out.println(buf.getByte(0) != sliced.getByte(0));
//        assert buf.getByte(0) != sliced.getByte(0);

        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(1000);
        byteBuf.writeBytes("中国你好".getBytes());
        System.out.println(byteBuf);
        System.out.println(ByteBufUtil.hexDump(byteBuf).length());;

        ChannelOutboundHandler channelOutboundHandler;
    }
}
