package xyz.mesr.backend;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.mesr.backend.log.Logger;
import xyz.mesr.backend.packet.PacketChannelInitializer;
import xyz.mesr.backend.packet.logging.PacketLoggingHandler;
import xyz.mesr.backend.user.User;
import java.util.ArrayList;

/**
 * Class representing the whole backend server
 */
public class BackendServer extends Thread{
    private final ArrayList<User> loadedUsers;
    private final int port = 4455;
    private ServerBootstrap serverBootstrap;
    public BackendServer() {
        this.loadedUsers = new ArrayList<>();
    }

    /**
     * Is run when the server is started
     */
    @Override
    public void run() {
        this.serverBootstrap = new ServerBootstrap();
        Logger.INSTANCE.information("Started Backend server! Thread: {" + this.getName() + "}");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new PacketLoggingHandler())
                    .childHandler(new PacketChannelInitializer());

            serverBootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        super.run();
    }
}
