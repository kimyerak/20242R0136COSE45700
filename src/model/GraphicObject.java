package model;

import java.awt.Graphics;

public interface GraphicObject {
    void draw(Graphics g); // 도형을 그리는 메서드 (Graphics 객체 인자로 받음)
    void move(int x, int y); // 도형을 이동시키는 메서드
    void resize(int width, int height); // 도형의 크기를 조절하는 메서드
    int getX(); // X 좌표 반환
    int getY(); // Y 좌표 반환
    int getWidth(); // 넓이 반환
    int getHeight(); // 높이 반환
}
