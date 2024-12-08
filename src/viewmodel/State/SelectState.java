package viewmodel.State;

import model.command.MoveCommand;
import model.command.SelectCommand;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

import java.util.List;

public class SelectState implements MouseState {
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
            List<GraphicObjectViewModel> selectedObjects = canvas.getSelectedObjects();
            MoveCommand moveCommand = new MoveCommand(canvas, selectedObjects, deltaX, deltaY);
            moveCommand.execute();
        }

        canvas.setDragStart(x, y);
    }

    @Override
    public void handleMouseUp(CanvasViewModel canvas, int x, int y) {
        // Optional: Additional behavior on mouse release
    }
}