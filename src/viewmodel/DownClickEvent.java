//viewmodel/downclickevent.java
package viewmodel;

public class DownClickEvent implements Event {
    private final CanvasViewModel canvasViewModel;

    public DownClickEvent(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handle(int x, int y) {
        GraphicObjectViewModel clickedObject = canvasViewModel.findObjectAt(x, y);

        if (clickedObject != null) {
            if (!canvasViewModel.isObjectSelected(clickedObject)) {
                canvasViewModel.deselectAllObjects();
                canvasViewModel.selectSingleObject(clickedObject);
            }
        } else {
            canvasViewModel.deselectAllObjects();
        }
    }
}
