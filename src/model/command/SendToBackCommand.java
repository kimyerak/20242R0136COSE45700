package model.command;

import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class SendToBackCommand implements Command, CanvasAwareCommand {
    private CanvasViewModel canvasViewModel;
    private GraphicObjectViewModel selectedObject;
    private int originalIndex;

    public SendToBackCommand(CanvasViewModel canvasViewModel, GraphicObjectViewModel selectedObject) {
        this.canvasViewModel = canvasViewModel;
        this.selectedObject = selectedObject;
    }

    @Override
    public void execute() {
        if (selectedObject != null) {
            originalIndex = canvasViewModel.getGraphicObjects().indexOf(selectedObject);
            canvasViewModel.getGraphicObjects().remove(selectedObject);
            canvasViewModel.getGraphicObjects().add(0, selectedObject);
            canvasViewModel.notifyObservers();
        }
    }

    @Override
    public void undo() {
        if (selectedObject != null) {
            canvasViewModel.getGraphicObjects().remove(selectedObject);
            canvasViewModel.getGraphicObjects().add(originalIndex, selectedObject);
            canvasViewModel.notifyObservers();
        }
    }

    @Override
    public void notifyCanvas() {
        canvasViewModel.notifyObservers();
    }
}
