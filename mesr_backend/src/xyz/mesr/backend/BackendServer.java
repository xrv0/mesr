package xyz.mesr.backend;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.mesr.backend.conf.Configuration;
import xyz.mesr.backend.conf.ConfigurationValue;
import xyz.mesr.backend.log.Logger;
import xyz.mesr.backend.packet.PacketChannelInitializer;
import xyz.mesr.backend.packet.logging.PacketLoggingHandler;
import xyz.mesr.backend.user.User;
import java.util.ArrayList;

/**
 * Class representing the whole backend server
 */
public class BackendServer extends Thread{
    private final Configuration configuration;
    private final ArrayList<User> loadedUsers;
    private ServerBootstrap serverBootstrap;

    @ConfigurationValue(name = "server.port")
    private int port = 4455;
    @ConfigurationValue(name = "server.name", description = "This name is not necessarily unique and showed to users in the server list")
    private String name = "mesr standard messaging server";
    @ConfigurationValue(name = "max_users")
    private int maxUsers;

    public BackendServer() {
        this.loadedUsers = new ArrayList<>();
        this.configuration = new Configuration("config.properties");
        this.configuration.saveValues(this);
    }

    /**
     * Is run when the server is started
     */
    @Override
    public void run() {
        System.out.println(this.port);

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
