package xyz.mesr.backend.packet.logging;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.StringUtil;
import xyz.mesr.backend.log.Logger;

import java.net.SocketAddress;

/**
 * Basically a copy of io.netty.handler.logging.LoggingHandler
 * except logger calls are being redirected to out own custom logger
 */
public class PacketLoggingHandler extends ChannelDuplexHandler {
    private static final String PREFIX = "[Network] ";
    private final Logger logger;

    public PacketLoggingHandler(){
        this.logger = Logger.INSTANCE;
    }

    private void debug(String message) {
       this.logger.debug(message, PREFIX);
    }

    /*
    Overrides all these methods and prints out a debug message
     */
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "REGISTERED"));
        ctx.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "UNREGISTERED"));
        ctx.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "ACTIVE"));
        ctx.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "INACTIVE"));
        ctx.fireChannelInactive();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.debug(this.format(ctx, "EXCEPTION", cause));
        ctx.fireExceptionCaught(cause);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        this.debug(this.format(ctx, "USER_EVENT", evt));
        ctx.fireUserEventTriggered(evt);
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "BIND", localAddress));
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "CONNECT", remoteAddress, localAddress));
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "DISCONNECT"));
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "CLOSE"));
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "DEREGISTER"));
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "READ COMPLETE"));
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.debug(this.format(ctx, "READ", msg));
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        this.debug(this.format(ctx, "WRITE", msg));
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "WRITABILITY CHANGED"));
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        this.debug(this.format(ctx, "FLUSH"));
    }

    protected String format(ChannelHandlerContext ctx, String eventName) {
        String chStr = ctx.channel().toString();
        return (new StringBuilder(chStr.length() + 1 + eventName.length())).append(chStr).append(' ').append(eventName).toString();
    }

    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf) {
            return formatByteBuf(ctx, eventName, (ByteBuf)arg);
        } else {
            return arg instanceof ByteBufHolder ? formatByteBufHolder(ctx, eventName, (ByteBufHolder)arg) : formatSimple(ctx, eventName, arg);
        }
    }

    protected String format(ChannelHandlerContext ctx, String eventName, Object firstArg, Object secondArg) {
        if (secondArg == null) {
            return formatSimple(ctx, eventName, firstArg);
        } else {
            String chStr = ctx.channel().toString();
            String arg1Str = String.valueOf(firstArg);
            String arg2Str = secondArg.toString();
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + arg1Str.length() + 2 + arg2Str.length());
            buf.append(chStr).append(' ').append(eventName).append(": ").append(arg1Str).append(", ").append(arg2Str);
            return buf.toString();
        }
    }

    private static String formatByteBuf(ChannelHandlerContext ctx, String eventName, ByteBuf msg) {
        String chStr = ctx.channel().toString();
        int length = msg.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 4);
            buf.append(chStr).append(' ').append(eventName).append(": 0B");
            return buf.toString();
        } else {
            int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + 10 + 1 + 2 + rows * 80);
            buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B').append(StringUtil.NEWLINE);
            ByteBufUtil.appendPrettyHexDump(buf, msg);
            return buf.toString();
        }
    }

    private static String formatByteBufHolder(ChannelHandlerContext ctx, String eventName, ByteBufHolder msg) {
        String chStr = ctx.channel().toString();
        String msgStr = msg.toString();
        ByteBuf content = msg.content();
        int length = content.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 4);
            buf.append(chStr).append(' ').append(eventName).append(", ").append(msgStr).append(", 0B");
            return buf.toString();
        } else {
            int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 2 + 10 + 1 + 2 + rows * 80);
            buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).append(", ").append(length).append('B').append(StringUtil.NEWLINE);
            ByteBufUtil.appendPrettyHexDump(buf, content);
            return buf.toString();
        }
    }

    private static String formatSimple(ChannelHandlerContext ctx, String eventName, Object msg) {
        String chStr = ctx.channel().toString();
        String msgStr = String.valueOf(msg);
        StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length());
        return buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).toString();
    }
}
