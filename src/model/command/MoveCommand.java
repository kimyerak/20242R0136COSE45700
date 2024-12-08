package model.command;

import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private int deltaX, deltaY;
    private List<GraphicObjectViewModel> movedObjects;
    private List<int[]> previousPositions;

    public MoveCommand(CanvasViewModel canvasViewModel, List<GraphicObjectViewModel> movedObjects, int deltaX, int deltaY) {
        this.canvasViewModel = canvasViewModel;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.movedObjects = movedObjects;
    }

    @Override
    public void execute() {
        previousPositions = new ArrayList<>();
        for (GraphicObjectViewModel object : movedObjects) {
            previousPositions.add(new int[]{object.getX(), object.getY()});
            object.move(object.getX() + deltaX, object.getY() + deltaY);
        }
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        if (movedObjects != null && previousPositions != null) {
            for (int i = 0; i < movedObjects.size(); i++) {
                GraphicObjectViewModel object = movedObjects.get(i);
                int[] prevPos = previousPositions.get(i);
                object.move(prevPos[0], prevPos[1]);
            }
        }
        canvasViewModel.notifyObservers();
    }
}
