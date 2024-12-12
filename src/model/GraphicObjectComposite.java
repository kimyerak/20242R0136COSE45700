package model;

import viewmodel.CanvasViewModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicObjectComposite implements GraphicObject {
    private GraphicObject graphicObject;
    private CanvasViewModel canvasViewModel;
    private List<GraphicObject> children = new ArrayList<>();

    @Override
    public void draw(Graphics g) {
        for (GraphicObject object : children) {
            object.draw(g);
        }
    }

    @Override
    public void move(int x, int y) {
        for (GraphicObject object : children) {
            object.move(x, y);
        }
    }

    @Override
    public void resize(int width, int height) {
        for (GraphicObject object : children) {
            object.resize(width, height);
        }
    }

    public void setText(String newText) {
        for (GraphicObject object : children) {
            object.setText(newText);
        }
    }

    @Override
    public int getX() {
        return children.isEmpty() ? 0 : children.get(0).getX();
    }

    @Override
    public int getY() {
        return children.isEmpty() ? 0 : children.get(0).getY();
    }

    @Override
    public int getWidth() {
        return children.isEmpty() ? 0 : children.get(0).getWidth();
    }

    @Override
    public int getHeight() {
        return children.isEmpty() ? 0 : children.get(0).getHeight();
    }

    public String getText() {
        return children.isEmpty() ? "" : children.get(0).getText();
    }

    public CanvasViewModel getCanvasViewModel() {
        return canvasViewModel;
    }

    public void addGraphicObject(GraphicObject object) {
        if (object == this || containsAncestor(object)) {
            throw new IllegalArgumentException("Cannot add a composite to itself or its ancestor.");
        }
        children.add(object);
    }

    private boolean containsAncestor(GraphicObject object) {
        if (object instanceof GraphicObjectComposite) {
            GraphicObjectComposite composite = (GraphicObjectComposite) object;
            return composite.getChildren().contains(this) || composite.containsAncestor(this);
        }
        return false;
    }

    public void removeGraphicObject(GraphicObject object) {
        this.getChildren().remove(object);
    }

    public void clear() {
        children.clear();
    }

    public List<GraphicObject> getChildren() {
        return children;
    }
}
