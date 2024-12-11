package model;

import java.awt.Graphics;

public interface GraphicObject {
    void draw(Graphics g);
    void move(int x, int y);
    void resize(int width, int height);
    void setText(String text);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    String getText();
}
