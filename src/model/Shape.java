package model;

import java.awt.Graphics;

public abstract class Shape implements GraphicObject {
    protected int x, y; // 도형의 위치
    protected int width, height; // 도형의 크기

    public Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public abstract void draw(Graphics g); // 구체적인 도형에서 구현해야 함 (Graphics 객체 사용)
}
