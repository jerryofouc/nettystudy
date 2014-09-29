package io.github.jerryofouc.chapter1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/**
 * 功能介绍：
 *
 * @author zhangxiaojie
 *         Date:  8/24/14
 *         Time: 23:12
 */
public class PlainEchoServer {
    public static void serve(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            final Socket socket = serverSocket.accept();


            System.out.println("accept socket from remote");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        while(true){
                            String xx = bufferedReader.readLine();
                            printWriter.println(xx);
                            printWriter.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();;
        }
    }

    public static void main(String args[]) throws IOException {
        serve(10020);
    }


}
