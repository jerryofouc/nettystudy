package io.github.jerryofouc.chapter2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 功能介绍：
 *
 * @author zhangxiaojie
 *         Date:  9/10/14
 *         Time: 15:27
 */
public class EchoServer {
    private final int port;
    private EchoServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class).
                    localAddress(new InetSocketAddress(port)).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new EchoServerHandler()).addFirst(new OutboundTest1()).addFirst(new OutboundTest2());
                } });
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +  " started and listen on"  + f.channel().localAddress());
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }

    }

    public static class OutboundTest1 extends ChannelOutboundHandlerAdapter{
        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("bind evernt");
            super.bind(ctx, localAddress, promise);
        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("connected");
            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("write1");
  //          ReferenceCountUtil.release(msg);
            super.write(ctx, msg, promise);

        }
    }

    public static class OutboundTest2 extends ChannelOutboundHandlerAdapter{
        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("bind evernt");
            super.bind(ctx, localAddress, promise);
        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("connected");
            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("write2");
            //          ReferenceCountUtil.release(msg);
            super.write(ctx, msg, promise);

        }
    }

    public static void main(String args[]) throws InterruptedException {

        int port = 11221;
        new EchoServer(port).start();
    }
}
