package xyz.mesr.client.user;

import xyz.mesr.client.Client;
import xyz.mesr.client.ui.components.ColorPicker;
import xyz.mesr.client.ui.components.GradientPanel;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Config {

    private File configFile;
    public ChatUser user;
    public Client client;
    public Color startColor = Color.MAGENTA;
    public Color endColor = Color.BLUE;

    public Config(ChatUser chatUser, Client client) {
        this.user = chatUser;
        this.client = client;
        this.configFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "admin.txt");
        try {
            if (configFile.exists()) {
                loadConfig();
            } else {
                client.setNewUser(true);
                try {
                    PrintWriter writer = new PrintWriter(configFile);
                    writer.println("start:" + startColor.getRGB());
                    writer.println("end:" + endColor.getRGB());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() throws IOException {
        Scanner scanner = new Scanner(configFile);
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.contains(":")) {
                String[] content = line.split("\\:");

                switch (content[0].charAt(0)) {

                    case 'e':
                        endColor = new Color(Integer.parseInt(content[1]));
                        break;
                    case 's':
                        startColor = new Color(Integer.parseInt(content[1]));
                        break;
                }
                System.out.println(content[1] + endColor + startColor);

            }
        }

    }

    public File getConfigFile() {
        return configFile;
    }

    public ChatUser getUser() {
        return user;
    }

    public Client getClient() {
        return client;
    }

    public Color getStartColor() {
        return startColor;
    }

    public Color getEndColor() {
        return endColor;
    }
}
