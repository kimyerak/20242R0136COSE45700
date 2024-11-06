//viewmodel/upclickevent.java
package viewmodel;

public class UpClickEvent implements Event {
    private final CanvasViewModel canvasViewModel;

    public UpClickEvent(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    @Override
    public void handle(int x, int y) {
        // Do nothing on mouse release, keeping the multi-selection intact
    }
}
