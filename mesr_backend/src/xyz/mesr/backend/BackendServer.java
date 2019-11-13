package xyz.mesr.backend;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.mariadb.jdbc.MariaDbConnection;
import xyz.mesr.backend.conf.Configuration;
import xyz.mesr.backend.conf.ConfigurationValue;
import xyz.mesr.backend.log.Logger;
import xyz.mesr.backend.packet.PacketChannelInitializer;
import xyz.mesr.backend.packet.logging.PacketLoggingHandler;
import xyz.mesr.backend.user.User;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class representing the whole backend server
 */
public class BackendServer extends Thread{
    private final Configuration configuration;
    private final ArrayList<User> loadedUsers;
    private ServerBootstrap serverBootstrap;
    private MariaDbConnection mariaDbConnection;

    @ConfigurationValue(name = "server.port", description = "Port to open the mesr server connection on")
    private int port = 4455;
    @ConfigurationValue(name = "server.name", description = "This name is not necessarily unique and showed to users in the server list")
    private String name = "mesr standard messaging server";
    @ConfigurationValue(name = "max_users", description = "Maximal amount of concurrent users")
    private int maxUsers;
    @ConfigurationValue(name = "mariadb.hostname", description = "MariaDB hostname in IP or domain format")
    private String mariadbHostname = "127.0.0.1";
    @ConfigurationValue(name = "mariadb.port", description = "MariaDB Database port")
    private short mariadbPort = 3306;
    @ConfigurationValue(name = "mariadb.username", description = "MariaDB username")
    private String mariadbUsername = "root";
    @ConfigurationValue(name = "mariadb.port", description = "MariaDB password")
    private String mariadbPassword = "password123";

    public BackendServer() {
        this.loadedUsers = new ArrayList<>();
        this.configuration = new Configuration(new File("config.properties"), "mesr server configuration file");
        this.configuration.setObjectValues(this);
        this.connectMariaDB();
    }

    /**
     * Connects to the MariaDB database
     */
    private void connectMariaDB() {
        Logger.INSTANCE.information("Connecting to MariaDB database");
        try {
            this.mariaDbConnection = (MariaDbConnection) DriverManager.getConnection("jdbc:mariadb://" + mariadbHostname + ":" + mariadbPort, mariadbUsername, mariadbPassword);
            Logger.INSTANCE.information("Successfully connected to the database");
        } catch (SQLException e) {
            Logger.INSTANCE.error("There was an error when connecting to the database", e);
        }
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
