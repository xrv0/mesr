package xyz.mesr.client.ui.components;

import xyz.mesr.client.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;

public class GradientPanel extends JPanel {


    public Color startColor;
    public Color endColor;
    public boolean transparentControls = true;
    public int gradientFocus = 500;

    public Color getStartColor() {
        return startColor;
    }

    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    public Color getEndColor() {
        return endColor;
    }

    public void setEndColor(Color kEndColor) {
        this.endColor = kEndColor;
    }

    public boolean isTransparentControls() {
        return transparentControls;
    }

    public void setTransparentControls(boolean transparentControls) {
        this.transparentControls = transparentControls;
    }

    public int getGradientFocus() {
        return gradientFocus;
    }

    public void setGradientFocus(int gradientFocus) {
        this.gradientFocus = gradientFocus;
    }


    public GradientPanel() {
        if (isTransparentControls()) {
            setBg(true);
        } else {
            setBg(false);
        }

    }

    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        super.addMouseMotionListener(l); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.getParent().isVisible()) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, gradientFocus, h, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    private void setBg(boolean isOpaque) {
        Component[] components = this.getComponents();
        for (Component component : components) {

            ((JLabel) component).setOpaque(isOpaque);
            ((JCheckBox) component).setOpaque(isOpaque);
            ((JTextField) component).setOpaque(isOpaque);
            ((JPasswordField) component).setOpaque(isOpaque);
            ((JFormattedTextField) component).setOpaque(isOpaque);
            ((JToolBar) component).setOpaque(isOpaque);
            ((JRadioButton) component).setOpaque(isOpaque);

        }
    }

}
