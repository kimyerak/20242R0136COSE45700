package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextObject implements GraphicObject {
    private int x, y;
    private String text;
    private int width;

    public TextObject(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.width = calculateWidth(text);
    }

    private int calculateWidth(String text) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tempImage.createGraphics();
        FontMetrics metrics = g2d.getFontMetrics(new Font("Default", Font.PLAIN, 12)); // Adjust font if needed
        int textWidth = metrics.stringWidth(text);
        g2d.dispose();
        return textWidth;
    }

    @Override
    public void draw(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textHeight = metrics.getAscent(); // 텍스트의 높이

        System.out.println("Drawing text '" + text + "' at center (" + x + ", " + y + ")");
        g.drawString(text, x, y + textHeight);
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width + 2;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    public void setText(String newText) {
        this.text = newText;
        this.width = calculateWidth(newText);
    }

    public String getText() {
        return text;
    }
}
