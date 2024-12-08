package viewmodel.State;

public interface MouseState {
    void handleMouseDown(int x, int y);
    void handleMouseDrag(int x, int y);
    void handleMouseUp(int x, int y);
}
