package viewmodel.State;

import model.Rectangle;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class RectangleDrawingState implements MouseState {
    private CanvasViewModel canvasViewModel;
    private int startX, startY;

    public RectangleDrawingState(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handleMouseDown(int x, int y) {
        startX = x;
        startY = y;
    }

    @Override
    public void handleMouseDrag(int x, int y) {
        // 미리보기 구현 가능 (선택 사항)
    }

    @Override
    public void handleMouseUp(int x, int y) {
        Rectangle rectangle = new Rectangle(startX, startY, Math.abs(x - startX), Math.abs(y - startY));
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(rectangle, canvasViewModel));
        canvasViewModel.notifyObservers();
    }
}
