package viewmodel;

import model.command.Command;
import model.command.CommandManager;
import viewmodel.State.MouseState;
import viewmodel.State.SelectState;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import model.*;
import model.Rectangle;
import view.PropertyPanelView;

import java.util.ArrayList;
import java.util.List;

public class CanvasViewModel {
    private List<GraphicObjectViewModel> graphicObjects;
    private GraphicObjectViewModel selectedObject = null; // 선택된 도형
    private List<CanvasObserver> observers = new ArrayList<>(); // 옵저버 목록
    private List<GraphicObjectViewModel> selectedObjects = new ArrayList<>();

    public List<GraphicObjectViewModel> getSelectedObjects() {
        return selectedObjects;
    }

    private PropertyPanelView propertyPanelView;
    private PropertyPanelViewModel propertyPanelViewModel;

    private MouseState currentState;

    public int prevX;
    public int prevY; // 마우스 이전 좌표

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    private CommandManager commandManager = new CommandManager();

    public void executeCommand(Command command) {
        commandManager.executeCommand(command);
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

    // 기본 생성자: 빈 리스트로 초기화
    public CanvasViewModel() {
        this.graphicObjects = new ArrayList<>();
        this.currentState = new SelectState(); // 초기 상태
    }

    public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel) {
        this.graphicObjects = new ArrayList<>();
        this.currentState = new SelectState();
        this.addObserver(propertyPanelViewModel);
        this.propertyPanelViewModel = propertyPanelViewModel;
    }

    public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel, PropertyPanelView propertyPanelView) {
        this.graphicObjects = new ArrayList<>();
        this.currentState = new SelectState();
        this.addObserver(propertyPanelViewModel);
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.propertyPanelView = propertyPanelView; // PropertyPanelView 초기화
    }

    public boolean hasSelectedObjects() {
        return !selectedObjects.isEmpty();
    }

    public boolean isObjectSelected(GraphicObjectViewModel object) {
        return selectedObjects.contains(object);
    }

    public void setDragStart(int x, int y) {
        prevX = x;
        prevY = y;
    }

    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Image image = ImageIO.read(file);
                ImageObject imageObject = new ImageObject(50, 50, 100, 100, file.getAbsolutePath()); // Default size and position
                addGraphicObject(new GraphicObjectViewModel(imageObject, this));
                notifyObservers(); // Notify to refresh the canvas
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to load image.");
            }
        }
    }

    public void addGraphicObject(GraphicObjectViewModel object) {
        graphicObjects.add(object);
    }

    public void addGraphicObjectByShapeType(String shapeType) {
        switch (shapeType) {
            case "Rectangle":
                this.addGraphicObject(new GraphicObjectViewModel(new model.Rectangle(50, 50, 100, 100), this));
                break;
            case "Ellipse":
                this.addGraphicObject(new GraphicObjectViewModel(new model.Ellipse(200, 100, 150, 80),this));
                break;
            case "TextObject":
                this.addGraphicObject(new GraphicObjectViewModel(new model.TextObject(300, 200, "Text"),this));
                break;
            case "Line":
                this.addGraphicObject(new GraphicObjectViewModel(new model.Line(100, 100, 200, 200),this)); // 기본 시작, 끝 좌표
                break;
            // 필요한 경우 다른 도형도 추가
        }
    }

    public void render(Graphics g) {
        for (GraphicObjectViewModel object : graphicObjects) {
            object.draw(g); // 각 객체 그리기
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        for (GraphicObjectViewModel object : selectedObjects) {
            g2d.drawRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
    }

    public void render(Graphics g, TextObject excludeObject) {
        for (GraphicObjectViewModel object : graphicObjects) {
            if (object.getGraphicObject() != excludeObject) {
                object.draw(g);
            }
        }
    }

    public void handleMousePressed(int x, int y, int button) {
        if (currentState != null) {
            currentState.handleMouseDown(this, x, y);
        }
    }

    public void handleMouseDrag(int x, int y) {
        if (currentState != null) {
            currentState.handleMouseDrag(this, x, y);
        }
    }

    public void handleMouseReleased(int x, int y) {
        if (currentState != null) {
            currentState.handleMouseUp(this, x, y);
        }
    }

    public void addObserver(CanvasObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (CanvasObserver observer : observers) {
            observer.onCanvasChanged();
        }
    }

    public void selectObjectAt(int x, int y) {
        selectedObject = findObjectAt(x, y);
        propertyPanelViewModel.setSelectedObject(selectedObject);
        propertyPanelView.updateProperties();
        propertyPanelViewModel.onCanvasChanged();
        notifyObservers();
    }

    public void selectSingleObject(GraphicObjectViewModel object) {
        deselectAllObjects();
        selectedObjects.add(object);
        notifyObservers();
    }

    public void selectObjectsInArea(int startX, int startY, int endX, int endY) {
        selectedObjects.clear(); // Clear previous selections

        // Calculate the selection bounds
        int minX = Math.min(startX, endX);
        int minY = Math.min(startY, endY);
        int maxX = Math.max(startX, endX);
        int maxY = Math.max(startY, endY);
        java.awt.Rectangle selectionArea = new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);

        // Select objects that intersect with the selection area
        for (GraphicObjectViewModel objectViewModel : graphicObjects) {
            java.awt.Rectangle objectBounds = new java.awt.Rectangle(
                    objectViewModel.getX(),
                    objectViewModel.getY(),
                    objectViewModel.getWidth(),
                    objectViewModel.getHeight()
            );

            // Check if the selection area intersects the object bounds
            if (selectionArea.intersects(objectBounds)) {
                selectedObjects.add(objectViewModel);
            }
        }

        notifyObservers(); // Notify observers about the selection change
    }

    public void deselectAllObjects() {
        selectedObjects.clear();
        notifyObservers();
    }

    public TextObject findTextObjectAt(int x, int y) {
        for (GraphicObjectViewModel objectViewModel : graphicObjects) {
            GraphicObject graphicObject = objectViewModel.getGraphicObject();
            if (graphicObject instanceof TextObject) {
                TextObject textObject = (TextObject) graphicObject;
                if (x >= textObject.getX() && x <= textObject.getX() + textObject.getWidth() &&
                        y >= textObject.getY() && y <= textObject.getY() + textObject.getHeight()) {
                    return textObject;
                }
            }
        }
        return null;
    }

    public void updateTextObject() {
        notifyObservers();
    }

    public GraphicObjectViewModel findObjectAt(int x, int y) {
        for (GraphicObjectViewModel object : graphicObjects) {
            if (x >= object.getX() && x <= object.getX() + object.getWidth() &&
                    y >= object.getY() && y <= object.getY() + object.getHeight()) {
                return object;
            }
        }
        return null;
    }
}
