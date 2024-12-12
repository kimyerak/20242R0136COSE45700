package model.command;

import model.GraphicObjectComposite;
import viewmodel.CanvasViewModel;

public class ClearCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private GraphicObjectComposite removedObjects = new GraphicObjectComposite();

    public ClearCommand(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void execute() {
        if (removedObjects.getChildren().isEmpty()) {
            removedObjects.getChildren().addAll(canvasViewModel.getGraphicObjects());
        }
        canvasViewModel.clearAllObjects();
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        if (!removedObjects.getChildren().isEmpty()) {
            GraphicObjectComposite composite = new GraphicObjectComposite();
            composite.getChildren().addAll(removedObjects.getChildren());
            canvasViewModel.getGraphicObjects().add(composite);
            canvasViewModel.notifyObservers();
        }
    }
}