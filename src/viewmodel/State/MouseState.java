package viewmodel.State;

import viewmodel.CanvasViewModel;

public interface MouseState {
    void handleMouseDown(CanvasViewModel canvas, int x, int y);
    void handleMouseDrag(CanvasViewModel canvas,int x, int y);
    void handleMouseUp(CanvasViewModel canvas,int x, int y);
}
