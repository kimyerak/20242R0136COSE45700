package model.command;

import model.GraphicObjectComposite;
import viewmodel.CanvasViewModel;

public class SendToBackCommand implements Command, CanvasAwareCommand {
    private CanvasViewModel canvasViewModel;
    private GraphicObjectComposite selectedComposite;
    private int originalIndex;

    public SendToBackCommand(CanvasViewModel canvasViewModel, GraphicObjectComposite selectedComposite) {
        this.canvasViewModel = canvasViewModel;
        this.selectedComposite = selectedComposite;
    }

    @Override
    public void execute() {
        if (selectedComposite != null) {
            originalIndex = canvasViewModel.getGraphicObjects().indexOf(selectedComposite);
            canvasViewModel.getGraphicObjects().remove(selectedComposite);
            canvasViewModel.getGraphicObjects().add(0, selectedComposite);
            canvasViewModel.notifyObservers();
        }
    }

    @Override
    public void undo() {
        if (selectedComposite != null) {
            canvasViewModel.getGraphicObjects().remove(selectedComposite);
            canvasViewModel.getGraphicObjects().add(originalIndex, selectedComposite);
            canvasViewModel.notifyObservers();
        }
    }

    @Override
    public void notifyCanvas() {
        canvasViewModel.notifyObservers();
    }
}
