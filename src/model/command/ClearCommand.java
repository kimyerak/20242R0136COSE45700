package model.command;

import model.GraphicObjectComposite;
import viewmodel.CanvasViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private List<GraphicObjectComposite> removedObjects = new ArrayList<>();

    public ClearCommand(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void execute() {
        if (removedObjects.isEmpty()) {
            removedObjects.addAll(canvasViewModel.getGraphicObjects());
        }
        canvasViewModel.clearAllObjects();
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        if (!removedObjects.isEmpty()) {
            canvasViewModel.getGraphicObjects().addAll(removedObjects);
            canvasViewModel.notifyObservers();
        }
    }
}
