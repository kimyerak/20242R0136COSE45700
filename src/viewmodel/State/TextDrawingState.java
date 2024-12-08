package viewmodel.State;

import model.TextObject;
import viewmodel.CanvasViewModel;
import viewmodel.GraphicObjectViewModel;

public class TextDrawingState implements MouseState {
    private CanvasViewModel canvasViewModel;

    public TextDrawingState(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handleMouseDown(int x, int y) {
        // 텍스트 추가 작업
        TextObject text = new TextObject(x, y, "New Text");
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(text, canvasViewModel));
        canvasViewModel.notifyObservers();
    }

    @Override
    public void handleMouseDrag(int x, int y) {
        // 텍스트는 드래그 동작 없음
    }

    @Override
    public void handleMouseUp(int x, int y) {
        // 텍스트는 릴리스 동작 없음
    }
}
