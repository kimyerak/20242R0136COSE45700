package viewmodel;

public class DownClickEvent implements Event {
    private final CanvasViewModel canvasViewModel;

    public DownClickEvent(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handle(int x, int y) {
        canvasViewModel.selectObjectAt(x, y);  // 도형 선택
    }
}
