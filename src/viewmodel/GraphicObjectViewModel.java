package viewmodel;
import model.GraphicObject;
import java.awt.Graphics;

public class GraphicObjectViewModel {
    private GraphicObject graphicObject;
    private CanvasViewModel canvasViewModel;

    public GraphicObjectViewModel(GraphicObject graphicObject, CanvasViewModel canvasViewModel) {

        this.graphicObject = graphicObject;
        this.canvasViewModel = canvasViewModel;
    }
    public CanvasViewModel getCanvasViewModel() {
        return canvasViewModel;
    }
    // 그래픽 객체 그리기
    public void draw(Graphics g) {
        graphicObject.draw(g);
    }

    // 그래픽 객체 이동
    public void move(int x, int y) {
        graphicObject.move(x, y);
    }

    // 그래픽 객체 크기 조절
    public void resize(int width, int height) {
        graphicObject.resize(width, height);
    }

    // 그래픽 객체의 X 위치 가져오기
    public int getX() {
        return graphicObject.getX();
    }

    // 그래픽 객체의 Y 위치 가져오기
    public int getY() {
        return graphicObject.getY();
    }

    // 그래픽 객체의 너비 가져오기
    public int getWidth() {
        return graphicObject.getWidth();
    }

    // 그래픽 객체의 높이 가져오기
    public int getHeight() {
        return graphicObject.getHeight();
    }
    // 그래픽 객체의 타입을 반환하는 메서드 (사각형, 타원, 선 등)
    public String getGraphicObjectType() {
        if (graphicObject instanceof model.Rectangle) {
            return "Rectangle";
        } else if (graphicObject instanceof model.Ellipse) {
            return "Ellipse";
        } else if (graphicObject instanceof model.Line) {
            return "Line";
        } else if (graphicObject instanceof model.TextObject) {
            return "TextObject";
        } else if (graphicObject instanceof model.ImageObject) {
            return "ImageObject";
        }
        return "Unknown";
    }
    // GraphicObject 객체를 반환하는 메서드
    public GraphicObject getGraphicObject() {
        return graphicObject;
    }
}

