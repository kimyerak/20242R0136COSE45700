package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextObject implements GraphicObject {
    private int x, y; // 텍스트의 위치
    private String text; // 표시할 텍스트
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
        // 텍스트 크기 측정
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textHeight = metrics.getAscent(); // 텍스트의 높이

        int centerY = y + textHeight;

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
        // 텍스트 객체는 크기를 변경할 필요가 없으므로 비워둡니다.
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
        // 텍스트는 한 줄로 가정하고 높이를 반환 (임의의 값)
        return 18;  // 기본 폰트 높이로 20픽셀로 가정
    }
//텍스트 내용변경
    public void setText(String newText) {
        this.text = newText;
        this.width = calculateWidth(newText);
    }
    public String getText() {
        return text;
    }
}
