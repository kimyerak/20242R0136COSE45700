package model;

import java.awt.*;

public class Line implements GraphicObject {
    private int x1, y1;
    private int x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        System.out.println("Drawing a line from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
        g.setColor(Color.BLUE);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void move(int x, int y) {
        int deltaX = x - x1;
        int deltaY = y - y1;
        this.x1 = x;
        this.y1 = y;
        this.x2 += deltaX;
        this.y2 += deltaY;
    }

    @Override
    public void resize(int width, int height) {
        this.x2 = x1 + width;
        this.y2 = y1 + height;
    }

    @Override
    public int getX() {
        return x1;
    }

    @Override
    public int getY() {
        return y1;
    }

    @Override
    public int getWidth() {
        return Math.abs(x2 - x1);
    }

    @Override
    public int getHeight() {
        return Math.abs(y2 - y1);
    }

    public void setText(String newText) {
    }

    public String getText() {
        return "";
    }
}
