package model;

import javax.swing.*;
import java.awt.*;

public class ShapeIcon implements Icon {
    private int width;
    private int height;
    private Color color;
    private String shape;

    public ShapeIcon(int width, int height, Color color, String shape) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.shape = shape;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        switch (shape) {
            case "Rectangle":
                g.fillRect(x, y, width, height);
                break;
            case "Ellipse":
                g.fillOval(x, y, width, height);
                break;
            case "Line":
                g.drawLine(x, y, x + width, y + height);
                break;
            case "TextObject":
                g.drawString("Text", x, y + height / 2 + 4);
                break;
            case "Image":
                g.drawString("Image", x, y + height / 2 + 4);
                break;
            case "Click":
                g.fillPolygon(
                        new int[]{x, x + width / 2, x},
                        new int[]{y, y + height / 2, y + height},
                        3
                );
                break;
        }
    }
}
