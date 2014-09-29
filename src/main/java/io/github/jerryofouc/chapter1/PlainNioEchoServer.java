package io.github.jerryofouc.chapter1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能介绍：
 *
 * @author zhangxiaojie
 *         Date:  8/24/14
 *         Time: 23:46
 */
public class PlainNioEchoServer {
    public static void serve(int port) throws IOException {
        System.out.println("listen port " + port);
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = socketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        serverSocket.bind(inetSocketAddress);
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectKeys.iterator();
            while(selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                try{
                    selectionKeyIterator.remove();
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();

                        SocketChannel client = serverSocketChannel.accept();

                        System.out.println("accept connect from client");
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
                    }else if(selectionKey.isReadable()){
                        SocketChannel readSocketChannel = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                        readSocketChannel.read(byteBuffer);
                    }else if(selectionKey.isWritable()){
                        SocketChannel client = (SocketChannel)selectionKey.channel();

                        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();

                        byteBuffer.flip();
                        client.write(byteBuffer);
                        byteBuffer.compact();

                    }
                }catch (IOException e){
                    selectionKey.cancel();;
                    try{
                        selectionKey.channel().close();;
                    }catch (IOException e1){

                    }
                }

            }
        }
    }

    public static void main(String args[]) throws IOException {
        serve(10020);
    }
}
