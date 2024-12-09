package viewmodel;
import model.GraphicObject;
import model.TextObject;
import view.CanvasView;
import view.PropertyPanelView;

public class PropertyPanelViewModel implements CanvasObserver{
    private CanvasViewModel canvasViewModel;
    private GraphicObjectViewModel selectedObject;
    private String selectedShapeType = "No object selected"; // 처음 기본 도형 타입
    private PropertyPanelView propertyPanelView;

    public PropertyPanelViewModel() {
        this.propertyPanelView = null; // PropertyPanelView를 나중에 설정할 수 있도록 초기화
    }

    public GraphicObjectViewModel getSelectedObject() {
        return selectedObject;
    }

    public void setCanvasViewModel(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
    }

    public CanvasViewModel getCanvasViewModel() {
        return selectedObject != null ? selectedObject.getCanvasViewModel() : canvasViewModel;
    }

    // 선택된 객체 설정
    public void setSelectedObject(GraphicObjectViewModel object) {
        this.selectedObject = object;
        // 객체에 따라 타입 설정
        if (object != null) {
            System.out.println("setSelectedText에서 널이 아님!!!!!!!!!!!!!!!");
            selectedShapeType = object.getGraphicObjectType();
        }else {
            selectedShapeType = "No object selected";
        }
        onCanvasChanged();
    }

    // 선택된 객체의 위치 및 크기 속성 업데이트
    public void updatePositionAndSize(int x, int y, int width, int height) {
        if (selectedObject != null) {
            selectedObject.move(x, y);
            selectedObject.resize(width, height);
    // CanvasViewModel에 변경 사항 알림
            selectedObject.getCanvasViewModel().notifyObservers();
        }
    }

    // 선택된 객체의 속성을 가져오기
    public String getObjectProperties() {
        if (selectedObject == null) {
            return "No object selected";
        }
        return "X: " + selectedObject.getX() +
                ", Y: " + selectedObject.getY() +
                ", Width: " + selectedObject.getWidth() +
                ", Height: " + selectedObject.getHeight();
    }
    // 선택된 객체의 X 좌표 가져오기
    public int getSelectedObjectX() {
        return selectedObject != null ? selectedObject.getX() : 0;
    }

    // 선택된 객체의 Y 좌표 가져오기
    public int getSelectedObjectY() {
        return selectedObject != null ? selectedObject.getY() : 0;
    }

    // 선택된 객체의 너비 가져오기
    public int getSelectedObjectWidth() {
        return selectedObject != null ? selectedObject.getWidth() : 0;
    }

    // 선택된 객체의 높이 가져오기
    public int getSelectedObjectHeight() {
        return selectedObject != null ? selectedObject.getHeight() : 0;
    }
    // 도형 타입 설정
    public void setSelectedShapeType(String shapeType) {
        this.selectedShapeType = shapeType;
    }

    // 선택된 도형 타입 반환
    public String getSelectedShapeType() {
        return selectedShapeType;
    }

    // 텍스트 업데이트
    public void updateText(String newText) {
        if (selectedObject != null && selectedObject.getGraphicObject() instanceof TextObject) {
            // selectedObject의 실제 객체가 TextObject인지 확인 후 캐스팅
            TextObject textObject = (TextObject) selectedObject.getGraphicObject();
            textObject.setText(newText);
        } else {
            System.out.println("Selected object is not a TextObject.");
        }
    }

    // 텍스트 가져오기
    public String getText() {
        if (selectedObject != null && selectedObject.getGraphicObject() instanceof TextObject) {
            TextObject textObject = (TextObject) selectedObject.getGraphicObject();
            return textObject.getText();
        }
        return "";
    }

    @Override
    public void onCanvasChanged() {
        System.out.println("onCanvasChanged called in PropertyPanelViewModel");
        if (selectedObject != null) {
            System.out.println("OnCanvasChanged에서 널이 아님!!!!!!");
            selectedShapeType = selectedObject.getGraphicObjectType();
        } else {
            // 선택된 객체가 없을 경우, 기본 메시지 설정
            selectedShapeType = "No object selected";
        }
        // 콘솔 로그에 출력
        System.out.println(selectedShapeType);

        // PropertyPanelView를 통해 화면에 출력
        if (propertyPanelView != null) {
            propertyPanelView.updateProperties(); // updateProperties 메서드를 통해 화면에 표시
        }
    }


}

