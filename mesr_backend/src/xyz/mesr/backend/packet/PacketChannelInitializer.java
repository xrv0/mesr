package xyz.mesr.backend.packet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class PacketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeLine = socketChannel.pipeline();

        /*
        Add those StringEncoder and Decoder first and then our own Handler
         */
        pipeLine.addFirst(new StringEncoder());
        pipeLine.addFirst(new StringDecoder());
        pipeLine.addLast(new PacketHandler());
    }
}
