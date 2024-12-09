package model.command;

import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private int totalDeltaX = 0, totalDeltaY = 0;
    private int appliedDeltaX = 0, appliedDeltaY = 0;
    private List<GraphicObjectViewModel> movedObjects;
    private List<int[]> previousPositions;

    public MoveCommand(CanvasViewModel canvasViewModel, List<GraphicObjectViewModel> movedObjects) {
        this.canvasViewModel = canvasViewModel;
        this.movedObjects = movedObjects;

        this.previousPositions = new ArrayList<>();
        for (GraphicObjectViewModel object : movedObjects) {
            this.previousPositions.add(new int[]{object.getX(), object.getY()});
        }
    }

    public void updateDeltas(int deltaX, int deltaY) {
        totalDeltaX += deltaX;
        totalDeltaY += deltaY;
    }

    public void applyMovement(int deltaX, int deltaY) {
        appliedDeltaX += deltaX;
        appliedDeltaY += deltaY;
        for (GraphicObjectViewModel object : movedObjects) {
            object.move(object.getX() + deltaX, object.getY() + deltaY);
        }
    }

    @Override
    public void execute() {
        int remainingDeltaX = totalDeltaX - appliedDeltaX;
        int remainingDeltaY = totalDeltaY - appliedDeltaY;

        for (GraphicObjectViewModel object : movedObjects) {
            object.move(object.getX() + remainingDeltaX, object.getY() + remainingDeltaY);
        }

        appliedDeltaX = 0;
        appliedDeltaY = 0;

        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        for (int i = 0; i < movedObjects.size(); i++) {
            GraphicObjectViewModel object = movedObjects.get(i);
            int[] prevPos = previousPositions.get(i);
            object.move(prevPos[0], prevPos[1]);
        }
        canvasViewModel.notifyObservers();
    }
}
