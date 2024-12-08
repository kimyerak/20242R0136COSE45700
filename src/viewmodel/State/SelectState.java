package viewmodel.State;

import viewmodel.CanvasViewModel;

public class SelectState implements MouseState {
    private CanvasViewModel canvasViewModel;

    public SelectState(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handleMouseDown(int x, int y) {
        canvasViewModel.selectObjectAt(x, y); // 선택 로직
    }

    @Override
    public void handleMouseDrag(int x, int y) {
        canvasViewModel.moveSelectedObjects(x - canvasViewModel.prevX, y - canvasViewModel.prevY);
        canvasViewModel.setDragStart(x, y);
    }

    @Override
    public void handleMouseUp(int x, int y) {
        // 선택 종료 시 추가 작업 가능
    }
}
