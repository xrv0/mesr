package xyz.mesr.client.ui;

import xyz.mesr.client.Client;
import xyz.mesr.client.ui.components.GradientPanel;

import javax.swing.*;

public class MainUI extends JFrame {

    public Client client;
    GradientPanel gradientPanel;

    public MainUI(Client client) {
        this.client = client;
        gradientPanel = new GradientPanel();
        gradientPanel.setStartColor(client.getConfig().getStartColor());
        gradientPanel.setEndColor(client.getConfig().getEndColor());
        gradientPanel.setGradientFocus(600);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        add(gradientPanel);
        setVisible(true);

    }


}
