package xyz.mesr.backend.packet.logging;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xyz.mesr.backend.log.Logger;

public class PacketLoggingHandler extends LoggingHandler {


    public PacketLoggingHandler(){
        super(LogLevel.INFO);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Logger.INSTANCE.information("Adding a new handler for Channel" + ctx.channel().toString());
    }
}
