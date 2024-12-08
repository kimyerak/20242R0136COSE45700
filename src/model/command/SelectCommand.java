package model.command;

import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private List<GraphicObjectViewModel> previousSelection;
    private List<GraphicObjectViewModel> newSelection;

    public SelectCommand(CanvasViewModel canvasViewModel, GraphicObjectViewModel clickedObject) {
        this.canvasViewModel = canvasViewModel;
        this.previousSelection = new ArrayList<>(canvasViewModel.getSelectedObjects());
        this.newSelection = new ArrayList<>();

        if (clickedObject != null) {
            newSelection.add(clickedObject);
        }
    }

    @Override
    public void execute() {
        canvasViewModel.deselectAllObjects();
        for (GraphicObjectViewModel object : newSelection) {
            canvasViewModel.selectSingleObject(object);
        }
    }

    @Override
    public void undo() {
        canvasViewModel.deselectAllObjects();
        for (GraphicObjectViewModel object : previousSelection) {
            canvasViewModel.selectSingleObject(object);
        }
    }
}
