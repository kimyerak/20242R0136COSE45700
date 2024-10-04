package viewmodel;

public class PropertyPanelViewModel {
    private GraphicObjectViewModel selectedObject;
    private String selectedShapeType = "Rectangle"; // 처음 기본 도형 타입

    // 선택된 객체 설정
    public void setSelectedObject(GraphicObjectViewModel object) {
        this.selectedObject = object;
        // 객체에 따라 타입 설정
        if (object != null) {
            selectedShapeType = object.getGraphicObjectType();
        }
    }

    // 선택된 객체의 위치 및 크기 속성 업데이트
    public void updatePositionAndSize(int x, int y, int width, int height) {
        if (selectedObject != null) {
            selectedObject.move(x, y);
            selectedObject.resize(width, height);
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

    // 도형 타입 설정
    public void setSelectedShapeType(String shapeType) {
        this.selectedShapeType = shapeType;
    }

    // 선택된 도형 타입 반환
    public String getSelectedShapeType() {
        return selectedShapeType;
    }


}

