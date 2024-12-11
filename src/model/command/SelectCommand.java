package model.command;

import model.GraphicObject;
import model.GraphicObjectComposite;
import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;

public class SelectCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private PropertyPanelViewModel propertyPanelViewModel;
    private GraphicObjectComposite previousSelection;
    private GraphicObject clickedObject;

    public SelectCommand(CanvasViewModel canvasViewModel, PropertyPanelViewModel propertyPanelViewModel, GraphicObject clickedObject) {
        this.canvasViewModel = canvasViewModel;
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.previousSelection = new GraphicObjectComposite();
        this.previousSelection.getChildren().addAll(canvasViewModel.getSelectedObjects().getChildren());
        this.clickedObject = clickedObject;
    }

    @Override
    public void execute() {
        if (clickedObject != null) {
            if (canvasViewModel.getSelectedObjects().getChildren().contains(clickedObject)) {
                canvasViewModel.getSelectedObjects().removeGraphicObject(clickedObject);
            } else {
                canvasViewModel.getSelectedObjects().addGraphicObject(clickedObject);
            }
            propertyPanelViewModel.setSelectedObjects(canvasViewModel.getSelectedObjects());
        }
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        canvasViewModel.deselectAllObjects();
        for (GraphicObject object : previousSelection.getChildren()) {
            canvasViewModel.getSelectedObjects().addGraphicObject(object);
        }
        propertyPanelViewModel.setSelectedObjects(canvasViewModel.getSelectedObjects());
        canvasViewModel.notifyObservers();
    }
}
