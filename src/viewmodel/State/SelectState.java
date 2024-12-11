package viewmodel.State;

import model.GraphicObject;
import model.GraphicObjectComposite;
import model.command.MoveCommand;
import model.command.SelectCommand;
import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;

public class SelectState implements MouseState {
    private MoveCommand currentMoveCommand;
    private boolean isDragging = false;

    @Override
    public void handleMouseDown(CanvasViewModel canvas, int x, int y) {
        isDragging = false;

        if (x >= canvas.boundingBoxX && x <= canvas.boundingBoxX + canvas.boundingBoxWidth &&
                y >= canvas.boundingBoxY && y <= canvas.boundingBoxY + canvas.boundingBoxHeight) {
            canvas.setDragStart(x, y);
            return;
        }

        GraphicObjectComposite composite = canvas.findObjectAt(x, y);
        GraphicObject clickedObject = composite != null && !composite.getChildren().isEmpty()
                ? composite.getChildren().get(0)
                : null;

        if (clickedObject != null) {
            if (canvas.getSelectedObjects().getChildren().contains(clickedObject)) {
                canvas.getSelectedObjects().removeGraphicObject(clickedObject);
            } else {
                PropertyPanelViewModel propertyPanelViewModel = canvas.getPropertyPanelViewModel();
                SelectCommand selectCommand = new SelectCommand(canvas, propertyPanelViewModel, clickedObject);
                canvas.executeCommand(selectCommand);
            }
        } else {
            canvas.deselectAllObjects();
        }

        canvas.setDragStart(x, y);
    }

    @Override
    public void handleMouseDrag(CanvasViewModel canvas, int x, int y) {
        isDragging = true;

        int deltaX = x - canvas.getPrevX();
        int deltaY = y - canvas.getPrevY();

        if (canvas.hasSelectedObjects()) {
            if (currentMoveCommand == null) {
                currentMoveCommand = new MoveCommand(canvas, canvas.getSelectedObjects());
            }
            currentMoveCommand.applyMovement(deltaX, deltaY);
        }

        canvas.setDragStart(x, y);
    }

    @Override
    public void handleMouseUp(CanvasViewModel canvas, int x, int y) {
        if (!isDragging) {
            GraphicObjectComposite composite = canvas.findObjectAt(x, y);
            GraphicObject clickedObject = composite != null && !composite.getChildren().isEmpty()
                    ? composite.getChildren().get(0)
                    : null;

            if (clickedObject != null) {
                if (canvas.getSelectedObjects().getChildren().contains(clickedObject)) {
                    canvas.getSelectedObjects().removeGraphicObject(clickedObject);
                } else {
                    PropertyPanelViewModel propertyPanelViewModel = canvas.getPropertyPanelViewModel();
                    SelectCommand selectCommand = new SelectCommand(canvas, propertyPanelViewModel, clickedObject);
                    canvas.executeCommand(selectCommand);
                }
            }
        }

        if (currentMoveCommand != null) {
            canvas.executeCommand(currentMoveCommand);
            currentMoveCommand = null;
        }

        isDragging = false;
        canvas.notifyObservers();
    }
}
