package xyz.mesr.backend.packet;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.mesr.backend.log.Logger;

public class PacketHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
     System.out.println(s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.INSTANCE.information("Establishing new channel with id " + ctx.channel().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logger.INSTANCE.information("Removing channel with id " + ctx.channel().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.INSTANCE.error(cause.getMessage());
        cause.printStackTrace(System.err);
    }
}
