package viewmodel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CanvasViewModel {
    private List<GraphicObjectViewModel> graphicObjects;
    private GraphicObjectViewModel selectedObject = null;  // 선택된 도형
    private int prevX, prevY;  // 마우스 이전 좌표

    private Event downClickEvent;
    private Event dragEvent;
    private Event upClickEvent;

    // 기본 생성자: 빈 리스트로 초기화
    public CanvasViewModel() {
        this.graphicObjects = new ArrayList<>();
        this.downClickEvent = new DownClickEvent(this);
        this.dragEvent = new DragEvent(this);
        this.upClickEvent = new UpClickEvent(this);
    }

    public CanvasViewModel(List<GraphicObjectViewModel> graphicObjects) {
        this.graphicObjects = graphicObjects;
        this.downClickEvent = new DownClickEvent(this);
        this.dragEvent = new DragEvent(this);
        this.upClickEvent = new UpClickEvent(this);
    }

    // 그래픽 객체 추가
    public void addGraphicObject(GraphicObjectViewModel object) {
        graphicObjects.add(object);
    }

    // 그래픽 객체 목록을 가져옴
    public List<GraphicObjectViewModel> getGraphicObjects() {
        return graphicObjects;
    }

    // 캔버스를 렌더링하는 메서드 (View에서 호출됨)
    public void render(Graphics g) {
        for (GraphicObjectViewModel object : graphicObjects) {
            object.draw(g);  // 각 객체 그리기
        }
    }

    // 마우스 이벤트 처리 메서드들
    public void handleMousePressed(int x, int y, int button) {
        downClickEvent.handle(x, y);  // DownClickEvent 호출
        prevX = x;
        prevY = y;
    }

    public void handleMouseDragged(int x, int y) {
        dragEvent.handle(x, y);  // DragEvent 호출
        prevX = x;
        prevY = y;
    }

    public void handleMouseReleased(int x, int y) {
        upClickEvent.handle(x, y);  // UpClickEvent 호출
    }

    // 선택된 도형 찾기
    public void selectObjectAt(int x, int y) {
        selectedObject = findObjectAt(x, y);
    }

    // 선택된 도형 이동
    public void moveSelectedObject(int x, int y) {
        if (selectedObject != null) {
            int deltaX = x - prevX;
            int deltaY = y - prevY;
            selectedObject.move(selectedObject.getX() + deltaX, selectedObject.getY() + deltaY);
        }
    }

    // 도형 선택 해제
    public void deselectObject() {
        selectedObject = null;
    }

    // 좌표에 있는 도형을 찾아 반환하는 메서드
    private GraphicObjectViewModel findObjectAt(int x, int y) {
        for (GraphicObjectViewModel object : graphicObjects) {
            if (x >= object.getX() && x <= object.getX() + object.getWidth() &&
                    y >= object.getY() && y <= object.getY() + object.getHeight()) {
                return object;
            }
        }
        return null;
    }
}
