package model;

import java.awt.*;

public class Ellipse implements GraphicObject {
    private int x, y, width, height;

    public Ellipse(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        System.out.println("Drawing an ellipse at (" + x + ", " + y + ") with width " + width + " and height " + height);
        g.setColor(Color.GREEN); // Set the fill color to green
        g.fillOval(x, y, width, height); // Fill the oval
        g.setColor(Color.BLACK); // Set the border color back to black (optional)
        g.drawOval(x, y, width, height); // Draw the oval border
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
}
