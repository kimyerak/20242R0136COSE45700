package viewmodel.State;

import model.command.MoveCommand;
import model.command.SelectCommand;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class SelectState implements MouseState {
    private MoveCommand currentMoveCommand;

    @Override
    public void handleMouseDown(CanvasViewModel canvas, int x, int y) {
        GraphicObjectViewModel clickedObject = canvas.findObjectAt(x, y);

        if (clickedObject != null) {
            if (!canvas.isObjectSelected(clickedObject)) {
                SelectCommand selectCommand = new SelectCommand(canvas, clickedObject);
                canvas.executeCommand(selectCommand);
            }
        } else {
            SelectCommand selectCommand = new SelectCommand(canvas, null);
            canvas.executeCommand(selectCommand);
        }

        canvas.setDragStart(x, y);
    }

    @Override
    public void handleMouseDrag(CanvasViewModel canvas, int x, int y) {
        int deltaX = x - canvas.getPrevX();
        int deltaY = y - canvas.getPrevY();

        if (canvas.hasSelectedObjects()) {
            if (currentMoveCommand == null) {
                currentMoveCommand = new MoveCommand(canvas, canvas.getSelectedObjects());
            }
            currentMoveCommand.updateDeltas(deltaX, deltaY);
            currentMoveCommand.applyMovement(deltaX, deltaY);
        }

        canvas.setDragStart(x, y);
    }

    @Override
    public void handleMouseUp(CanvasViewModel canvas, int x, int y) {
        if (currentMoveCommand != null) {
            canvas.executeCommand(currentMoveCommand);
            currentMoveCommand = null;
        }
    }
}