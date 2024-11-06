package model;

import java.awt.*;

public class TextObject implements GraphicObject {
    private int x, y; // 텍스트의 위치
    private String text; // 표시할 텍스트

    public TextObject(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        // 텍스트 크기 측정
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textHeight = metrics.getHeight(); // 텍스트의 높이

        int centerY = y + textHeight;

        System.out.println("Drawing text '" + text + "' at center (" + x + ", " + y + ")");
        g.drawString(text, x, centerY);
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
        // 텍스트 길이를 이용해 너비를 대략적으로 계산
        return text.length() * 10;  // 글자당 10픽셀 정도로 가정 (임의의 값)
    }

    @Override
    public int getHeight() {
        // 텍스트는 한 줄로 가정하고 높이를 반환 (임의의 값)
        return 20;  // 기본 폰트 높이로 20픽셀로 가정
    }
//텍스트 내용변경
    public void setText(String newText) {
        this.text = newText;
    }
    public String getText() {
        return text;
    }
}
