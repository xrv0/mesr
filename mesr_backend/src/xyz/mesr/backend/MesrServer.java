package xyz.mesr.backend;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.mesr.backend.conf.Configuration;
import xyz.mesr.backend.conf.ConfigurationValue;
import xyz.mesr.backend.log.Logger;
import xyz.mesr.backend.network.PacketChannelInitializer;
import xyz.mesr.backend.log.PacketLoggingHandler;
import xyz.mesr.backend.user.User;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class representing the whole backend server
 */
public class MesrServer extends Thread{
    private final Configuration configuration;
    private final ArrayList<User> loadedUsers;
    private ServerBootstrap serverBootstrap;
    private Connection sqlConnection;

    @ConfigurationValue(name = "server.port", description = "Port to open the mesr server connection on")
    private int port = 4455;
    @ConfigurationValue(name = "server.name", description = "This name is not necessarily unique and showed to users in the server list")
    private String name = "mesr messaging server";
    @ConfigurationValue(name = "max_users", description = "Maximal amount of concurrent users")
    private int maxUsers;
    @ConfigurationValue(name = "sql.hostname", description = "SQL Database domain or IP")
    private String sqlHostname = "127.0.0.1";
    @ConfigurationValue(name = "sql.port", description = "SQL Database port")
    private short sqlPort = 3306;
    @ConfigurationValue(name = "sql.username", description = "SQL Database username")
    private String sqlUsername = "root";
    @ConfigurationValue(name = "sql.port", description = "SQL Database password")
    private String sqlPassword = "password123";

    public MesrServer() {
        this.loadedUsers = new ArrayList<>();
        this.configuration = new Configuration(new File("config.properties"), "mesr server configuration file");
        this.configuration.saveValues(this);
        this.connectDatabase();
    }

    /**
     * Connects to the sql database
     */
    private void connectDatabase() {
        Logger.INSTANCE.information("Connecting to SQL database");
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            this.sqlConnection = DriverManager.getConnection("jdbc:mariadb://" + sqlHostname + ":" + sqlPort, sqlUsername, sqlPassword);
            Logger.INSTANCE.information("Successfully connected to the SQL database");
        } catch (SQLException e) {
            Logger.INSTANCE.error("There was an error when connecting to the database", e);
        } catch (ClassNotFoundException e) {
            Logger.INSTANCE.error("There was an error when loading the sql connector driver", e);
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
    }
}
