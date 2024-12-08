package viewmodel.State;

import model.Line;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class LineDrawingState implements MouseState {
    private CanvasViewModel canvasViewModel;
    private int startX, startY;

    public LineDrawingState(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handleMouseDown(int x, int y) {
        startX = x;
        startY = y;
    }

    @Override
    public void handleMouseDrag(int x, int y) {
        // 미리보기 처리 가능 (선택 사항)
    }

    @Override
    public void handleMouseUp(int x, int y) {
        Line line = new Line(startX, startY, x, y);
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(line, canvasViewModel));
        canvasViewModel.notifyObservers(); // 캔버스 업데이트
    }
}
