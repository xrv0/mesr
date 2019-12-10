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
    private ChannelFuture lastWriteFuture = null;
    private Channel ch;
    private EventLoopGroup group;
    private  Bootstrap bootstrap;

    @Override
    public void run() {

        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new PacketChannelConnector());

        try {
            ch = bootstrap.connect(host, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }


        while(this.isAlive()){ }
    }
    public void sendMessage(String message){
        lastWriteFuture = ch.writeAndFlush(message);
    }
    public Channel getChannel(){
        return ch;
    }
}
