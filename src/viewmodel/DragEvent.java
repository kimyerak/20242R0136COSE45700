//viewmodel/dragEvent.java
package viewmodel;

public class DragEvent implements Event {
    private final CanvasViewModel canvasViewModel;

    public DragEvent(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handle(int x, int y) {
        canvasViewModel.moveSelectedObject(x, y);  // 선택된 도형 이동
    }
}
