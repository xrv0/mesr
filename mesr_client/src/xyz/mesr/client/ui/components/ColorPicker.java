package xyz.mesr.client.ui.components;

import xyz.mesr.client.ui.components.GradientPanel;
import xyz.mesr.client.user.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ColorPicker extends JFrame {

    public Color firstColor;
    public Color secondColor;
    private JColorChooser jColorChooser;

    public ColorPicker(GradientPanel panel, Config config) {
        JOptionPane.showMessageDialog(panel, jColorChooser = new JColorChooser());
        firstColor = jColorChooser.getColor();
        if (secondColor == null) {
            JColorChooser s;
            JOptionPane.showMessageDialog(panel, s = new JColorChooser());
            secondColor = s.getColor();
        }
        panel.setStartColor(firstColor);
        panel.setEndColor(secondColor);
    }


}
