package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Picture implements GraphicObject {
    private List<GraphicObject> graphics = new ArrayList<>();

    @Override
    public void draw(Graphics g) {
        for (GraphicObject graphic : graphics) {
            graphic.draw(g);
        }
    }

    @Override
    public void move(int x, int y) {
        for (GraphicObject graphic : graphics) {
            graphic.move(x, y);
        }
    }

    @Override
    public void resize(int width, int height) {
        for (GraphicObject graphic : graphics) {
            graphic.resize(width, height);
        }
    }

    @Override
    public int getX() {
        // 적절한 방식으로 좌표를 반환하거나, 필요에 따라 구현합니다.
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    // 컴포지트 패턴의 Add, Remove, GetChild 메서드 구현
    public void add(GraphicObject graphic) {
        graphics.add(graphic);
    }

    public void remove(GraphicObject graphic) {
        graphics.remove(graphic);
    }

    public GraphicObject getChild(int index) {
        return graphics.get(index);
    }
}
