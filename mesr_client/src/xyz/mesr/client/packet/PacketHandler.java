package xyz.mesr.client.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.mesr.client.log.Logger;

public class PacketHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Logger.INSTANCE.information(s);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.INSTANCE.error(cause.getMessage());
        cause.printStackTrace(System.err);
    }
}
