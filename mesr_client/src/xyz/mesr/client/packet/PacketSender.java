package xyz.mesr.client.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PacketSender extends Thread{

    private final int port = 4455;
    private  String host = "127.0.0.1";

    public PacketSender(){

    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PacketChannelConnector());

            // Start the connection attempt.
            Channel ch;
            ch = b.connect(host, port).sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            Scanner sc = new Scanner(System.in);
            while (this.isAlive()) {
                if(sc.hasNext()){
                    String line = sc.nextLine();
                    lastWriteFuture = ch.writeAndFlush(line + "\r\n");
                }

            }
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
