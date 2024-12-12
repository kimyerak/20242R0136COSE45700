package viewmodel;

import model.*;
import model.Rectangle;
import model.command.Command;
import model.command.CommandManager;
import view.PropertyPanelView;
import viewmodel.State.MouseState;
import viewmodel.State.SelectState;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class CanvasViewModel {
    private List<GraphicObjectComposite> graphicObjects;
    private List<CanvasObserver> observers = new ArrayList<>();
    private GraphicObjectComposite selectedObjects = new GraphicObjectComposite();
    private PropertyPanelView propertyPanelView;
    private PropertyPanelViewModel propertyPanelViewModel;
    private MouseState currentState;
    private CommandManager commandManager = new CommandManager();
    private int prevX;
    private int prevY;
    public int boundingBoxX;
    public int boundingBoxY;
    public int boundingBoxWidth;
    public int boundingBoxHeight;

    public CanvasViewModel() {
        this.graphicObjects = new ArrayList<>();
        this.currentState = new SelectState();
    }

    public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel) {
        this();
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.addObserver(propertyPanelViewModel);
    }

    public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel, PropertyPanelView propertyPanelView) {
        this(propertyPanelViewModel);
        this.propertyPanelView = propertyPanelView;
    }

    public GraphicObjectComposite getSelectedObjects() {
        return selectedObjects;
    }

    public List<GraphicObjectComposite> getGraphicObjects() {
        return graphicObjects;
    }

    public PropertyPanelViewModel getPropertyPanelViewModel() {
        return this.propertyPanelViewModel;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void executeCommand(Command command) {
        commandManager.executeCommand(command);
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

    public boolean hasSelectedObjects() {
        return !selectedObjects.getChildren().isEmpty();
    }

    public void setDragStart(int x, int y) {
        this.prevX = x;
        this.prevY = y;
    }

    public void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                Image image = ImageIO.read(file);
                ImageObject imageObject = new ImageObject(50, 50, 100, 100, file.getAbsolutePath());
                addGraphicObject(imageObject);
                notifyObservers();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to load image.");
            }
        }
    }

    public void addGraphicObject(GraphicObject object) {
        GraphicObjectComposite composite = new GraphicObjectComposite();
        composite.addGraphicObject(object);
        graphicObjects.add(composite);
    }

    public void addGraphicObjectByShapeType(String shapeType) {
        switch (shapeType) {
            case "Rectangle":
                addGraphicObject(new Rectangle(50, 50, 100, 100));
                break;
            case "Ellipse":
                addGraphicObject(new Ellipse(200, 100, 150, 80));
                break;
            case "TextObject":
                addGraphicObject(new TextObject(300, 200, "Text"));
                break;
            case "Line":
                addGraphicObject(new Line(100, 100, 200, 200));
                break;
        }
    }

    public void clearAllObjects() {
        graphicObjects.clear();
        selectedObjects.clear();
        notifyObservers();
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

    public void calculateBoundingBox() {
        if (!selectedObjects.getChildren().isEmpty()) {
            int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

            for (GraphicObject selected : selectedObjects.getChildren()) {
                minX = Math.min(minX, selected.getX());
                minY = Math.min(minY, selected.getY());
                maxX = Math.max(maxX, selected.getX() + selected.getWidth());
                maxY = Math.max(maxY, selected.getY() + selected.getHeight());
            }

            boundingBoxX = minX;
            boundingBoxY = minY;
            boundingBoxWidth = maxX - minX;
            boundingBoxHeight = maxY - minY;
        }
    }

    public void render(Graphics g) {
        for (GraphicObjectComposite object : graphicObjects) {
            object.draw(g);
        }

        if (!selectedObjects.getChildren().isEmpty()) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setColor(new Color(0, 0, 255, 128));
            for (GraphicObject selected : selectedObjects.getChildren()) {
                g2d.drawRect(selected.getX(), selected.getY(), selected.getWidth(), selected.getHeight());
            }

            calculateBoundingBox();
            g2d.setColor(new Color(0, 0, 0, 128));
            g2d.drawRect(boundingBoxX, boundingBoxY, boundingBoxWidth, boundingBoxHeight);

            g2d.dispose();
        }
    }

    public void selectObjectAt(int x, int y) {
        GraphicObjectComposite composite = findObjectAt(x, y);
        if (composite != null) {
            if (selectedObjects.getChildren().contains(composite)) {
                selectedObjects.removeGraphicObject(composite);
            } else {
                selectedObjects.addGraphicObject(composite);
            }
        } else {
            deselectAllObjects();
        }
        propertyPanelViewModel.onCanvasChanged();
        notifyObservers();
    }

    public void deselectAllObjects() {
        selectedObjects.clear();
        notifyObservers();
    }

    public void addObserver(CanvasObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (CanvasObserver observer : observers) {
            observer.onCanvasChanged();
        }
        if (propertyPanelView != null) {
            SwingUtilities.getWindowAncestor(propertyPanelView).repaint();
        }
    }

    public GraphicObjectComposite findObjectAt(int x, int y) {
        for (int i = graphicObjects.size() - 1; i >= 0; i--) {
            GraphicObjectComposite composite = graphicObjects.get(i);
            if (x >= composite.getX() && x <= composite.getX() + composite.getWidth() &&
                    y >= composite.getY() && y <= composite.getY() + composite.getHeight()) {
                return composite;
            }
        }
        return null;
    }
}
