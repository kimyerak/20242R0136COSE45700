package model;

import java.awt.*;

public class Line implements GraphicObject {
    private int x1, y1; // 시작점 좌표
    private int x2, y2; // 끝점 좌표

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
        g.drawLine(x1, y1, x2, y2); // 실제로 선을 그리기
    }

    @Override
    public void move(int x, int y) {
        // 시작점과 끝점 모두 이동
        int deltaX = x - x1;
        int deltaY = y - y1;
        this.x1 = x;
        this.y1 = y;
        this.x2 += deltaX;
        this.y2 += deltaY;
    }

    @Override
    public void resize(int width, int height) {
        // 선은 특정 넓이와 높이를 가질 수 없으므로, 끝점만 이동하여 크기 변경 효과를 구현
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
}
