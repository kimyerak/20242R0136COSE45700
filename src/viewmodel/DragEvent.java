package viewmodel;

public class DragEvent implements Event {
    private final CanvasViewModel canvasViewModel;

    public DragEvent(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handle(int x, int y) {
        int deltaX = x - canvasViewModel.prevX;
        int deltaY = y - canvasViewModel.prevY;
        canvasViewModel.moveSelectedObjects(deltaX, deltaY);
        canvasViewModel.prevX = x;
        canvasViewModel.prevY = y;
    }
}
