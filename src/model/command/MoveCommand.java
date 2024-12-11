package model.command;

import model.GraphicObject;
import model.GraphicObjectComposite;
import viewmodel.CanvasViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {
    private CanvasViewModel canvasViewModel;
    private GraphicObjectComposite movedObjects;
    private List<int[]> previousPositions;
    private List<int[]> newPositions;

    public MoveCommand(CanvasViewModel canvasViewModel, GraphicObjectComposite movedObjects) {
        this.canvasViewModel = canvasViewModel;
        this.movedObjects = movedObjects;

        this.previousPositions = new ArrayList<>();
        for (GraphicObject object : movedObjects.getChildren()) {
            this.previousPositions.add(new int[]{object.getX(), object.getY()});
        }

        this.newPositions = new ArrayList<>();
    }

    public void applyMovement(int deltaX, int deltaY) {
        if (!movedObjects.getChildren().isEmpty()) {
            for (GraphicObject object : movedObjects.getChildren()) {
                object.move(object.getX() + deltaX, object.getY() + deltaY);
            }
        }
    }

    @Override
    public void execute() {
        if (newPositions.isEmpty()) {
            for (GraphicObject object : movedObjects.getChildren()) {
                newPositions.add(new int[]{object.getX(), object.getY()});
            }
        } else {
            for (int i = 0; i < movedObjects.getChildren().size(); i++) {
                GraphicObject object = movedObjects.getChildren().get(i);
                int[] newPos = newPositions.get(i);
                object.move(newPos[0], newPos[1]);
            }
        }
        canvasViewModel.notifyObservers();
    }

    @Override
    public void undo() {
        for (int i = 0; i < movedObjects.getChildren().size(); i++) {
            GraphicObject object = movedObjects.getChildren().get(i);
            int[] prevPos = previousPositions.get(i);
            object.move(prevPos[0], prevPos[1]);
        }
        canvasViewModel.notifyObservers();
    }
}
