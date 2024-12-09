package model.command;

import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private List<GraphicObjectViewModel> removedObjects;

    public ClearCommand(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void execute() {
        removedObjects = new ArrayList<>(canvasViewModel.getGraphicObjects());
        canvasViewModel.clearAllObjects();
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        if (removedObjects != null) {
            for (GraphicObjectViewModel object : removedObjects) {
                canvasViewModel.addGraphicObject(object);
            }
            canvasViewModel.notifyObservers();
        }
    }
}