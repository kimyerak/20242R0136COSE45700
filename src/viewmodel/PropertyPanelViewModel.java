package viewmodel;

import model.GraphicObject;
import model.GraphicObjectComposite;
import view.PropertyPanelView;

public class PropertyPanelViewModel implements CanvasObserver {
    private CanvasViewModel canvasViewModel;
    private GraphicObjectComposite selectedObjects = new GraphicObjectComposite();
    private String selectedShapeType = "No object selected";
    private PropertyPanelView propertyPanelView;

    public PropertyPanelViewModel() {
        this.propertyPanelView = null;
    }

    public GraphicObjectComposite getSelectedObjects() {
        return selectedObjects;
    }

    public void setCanvasViewModel(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    public CanvasViewModel getCanvasViewModel() {
        return canvasViewModel;
    }

    public void setSelectedObjects(GraphicObjectComposite objects) {
        this.selectedObjects = objects;
        updateSelectedObjectType();
        onCanvasChanged();
    }

    private void updateSelectedObjectType() {
        if (selectedObjects.getChildren().size() > 1) {
            selectedShapeType = "Multiple objects";
        } else if (!selectedObjects.getChildren().isEmpty()) {
            GraphicObject firstObject = selectedObjects.getChildren().get(0);
            selectedShapeType = firstObject.getClass().getSimpleName();
        } else {
            selectedShapeType = "No object selected";
        }
    }

    public void updatePositionAndSize(int x, int y, int width, int height, String text) {
        if (selectedObjects != null && !selectedObjects.getChildren().isEmpty()) {
            for (GraphicObject object : selectedObjects.getChildren()) {
                object.move(x, y);
                object.resize(width, height);
                object.setText(text);
            }
            canvasViewModel.notifyObservers();
        }
    }

    public int getSelectedObjectX() {
        return selectedObjects != null && !selectedObjects.getChildren().isEmpty()
                ? selectedObjects.getChildren().get(0).getX()
                : 0;
    }

    public int getSelectedObjectY() {
        return selectedObjects != null && !selectedObjects.getChildren().isEmpty()
                ? selectedObjects.getChildren().get(0).getY()
                : 0;
    }

    public int getSelectedObjectWidth() {
        return selectedObjects != null && !selectedObjects.getChildren().isEmpty()
                ? selectedObjects.getChildren().get(0).getWidth()
                : 0;
    }

    public int getSelectedObjectHeight() {
        return selectedObjects != null && !selectedObjects.getChildren().isEmpty()
                ? selectedObjects.getChildren().get(0).getHeight()
                : 0;
    }

    public String getSelectedObjectText() {
        return selectedObjects != null && !selectedObjects.getChildren().isEmpty()
                ? selectedObjects.getChildren().get(0).getText()
                : "";
    }

    public void setSelectedShapeType(String shapeType) {
        this.selectedShapeType = shapeType;
    }

    public String getSelectedShapeType() {
        return selectedShapeType;
    }

    @Override
    public void onCanvasChanged() {
        updateSelectedObjectType();
        if (propertyPanelView != null) {
            propertyPanelView.updateProperties();
        }
    }
}
