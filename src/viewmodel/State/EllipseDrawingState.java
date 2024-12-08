package viewmodel.State;

import model.Ellipse;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class EllipseDrawingState implements MouseState {
    private CanvasViewModel canvasViewModel;
    private int startX, startY;

    public EllipseDrawingState(CanvasViewModel canvasViewModel) {
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
        Ellipse ellipse = new Ellipse(startX, startY, Math.abs(x - startX), Math.abs(y - startY));
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(ellipse, canvasViewModel));
        canvasViewModel.notifyObservers();
    }
}
