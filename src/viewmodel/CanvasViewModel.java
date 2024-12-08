    package viewmodel;

    import javax.swing.*;
    import java.awt.*;
    import java.io.File;
    import java.io.IOException;
    import javax.imageio.ImageIO;
    import model.*; // Import model classes
    import model.Rectangle;
    import view.PropertyPanelView;
    import viewmodel.State.MouseState;
    import viewmodel.State.SelectState;

    import java.util.ArrayList;
    import java.util.List;

    public class CanvasViewModel {
        private List<GraphicObjectViewModel> graphicObjects;
        private GraphicObjectViewModel selectedObject = null;  // 선택된 도형
        private List<CanvasObserver> observers = new ArrayList<>();  // 옵저버 목록
        private List<GraphicObjectViewModel> selectedObjects = new ArrayList<>();
        private MouseState currentState;
        int prevX, prevY;  // 마우스 이전 좌표


        private PropertyPanelView propertyPanelView;
        private PropertyPanelViewModel propertyPanelViewModel;

        // 기본 생성자: 빈 리스트로 초기화
        public CanvasViewModel() {
            this.graphicObjects = new ArrayList<>();
            this.currentState = new SelectState(this);

        }

        public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel) {
            this.graphicObjects = new ArrayList<>();
            this.addObserver(propertyPanelViewModel);
            this.propertyPanelViewModel = propertyPanelViewModel;

        }
        public CanvasViewModel(PropertyPanelViewModel propertyPanelViewModel, PropertyPanelView propertyPanelView) {
            this.graphicObjects = new ArrayList<>();
            this.addObserver(propertyPanelViewModel);
            this.propertyPanelViewModel = propertyPanelViewModel;
            this.propertyPanelView = propertyPanelView; // PropertyPanelView 초기화
        }

        public boolean hasSelectedObjects() {
            return !selectedObjects.isEmpty();
        }

        public boolean isObjectSelected(GraphicObjectViewModel object) {
            return selectedObjects.contains(object);
        }

        public void setDragStart(int x, int y) {
            prevX = x;
            prevY = y;
        }
        public void setState(MouseState state) {
            this.currentState = state;
        }

        public void setMouseState(MouseState newState) {
            this.currentState = newState;
        }

        public void loadImage() {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    Image image = ImageIO.read(file);
                    ImageObject imageObject = new ImageObject(50, 50, 100, 100, file.getAbsolutePath()); // Default size and position
                    addGraphicObject(new GraphicObjectViewModel(imageObject, this));
                    notifyObservers(); // Notify to refresh the canvas
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to load image.");
                }
            }
        }

        // 그래픽 객체 추가
        public void addGraphicObject(GraphicObjectViewModel object) {
            graphicObjects.add(object);
        }
        public void addGraphicObjectByShapeType(String shapeType) {
            switch (shapeType) {
                case "Rectangle":
                    this.addGraphicObject(new GraphicObjectViewModel(new Rectangle(50, 50, 100, 100), this));
                    break;
                case "Ellipse":
                    this.addGraphicObject(new GraphicObjectViewModel(new Ellipse(200, 100, 150, 80),this));
                    break;
                case "TextObject":
                    this.addGraphicObject(new GraphicObjectViewModel(new TextObject(300, 200, "Text"),this));
                    break;
                case "Line":
                    this.addGraphicObject(new GraphicObjectViewModel(new Line(100, 100, 200, 200),this)); // 기본 시작, 끝 좌표
                    break;
                // 필요한 경우 다른 도형도 추가
            }
        }
        // 그래픽 객체 목록을 가져옴
        public List<GraphicObjectViewModel> getGraphicObjects() {
            return graphicObjects;
        }

        // 캔버스를 렌더링하는 메서드 (View에서 호출됨)
        public void render(Graphics g) {
            for (GraphicObjectViewModel object : graphicObjects) {
                object.draw(g);  // 각 객체 그리기

                if (selectedObjects.contains(object)) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(2)); // Thicker border for selection
                    g2d.drawRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                }
            }
        }

        public void render(Graphics g, TextObject excludeObject) {
            for (GraphicObjectViewModel object : graphicObjects) {
                if (object.getGraphicObject() != excludeObject) {
                    object.draw(g);
                }
            }
        }

        // 마우스 이벤트 처리 메서드들 3개
        public void handleMousePressed(int x, int y, int button) {
            if (currentState != null) {
                currentState.handleMouseDown(x, y);
            }
            //우클릭시 앞으로 가져오기 메뉴 띄우기 이런것도 가능할듯
        }

        public void handleMouseDragged(int x, int y) {
            if (currentState != null) {
                currentState.handleMouseDrag(x, y);
            }
        }

        public void handleMouseReleased(int x, int y) {
            if (currentState != null) {
                currentState.handleMouseUp(x, y);
            }
        }

        // 옵저버 등록 메서드
        public void addObserver(CanvasObserver observer) {
            observers.add(observer);
        }

        // 옵저버에게 변경 사항 알림
        public void notifyObservers() {
            for (CanvasObserver observer : observers) {
                observer.onCanvasChanged();
            }
        }

        // 선택된 도형 뭔지 전달
        public void selectObjectAt(int x, int y) {
            selectedObject = findObjectAt(x, y);
            propertyPanelViewModel.setSelectedObject(selectedObject);
            propertyPanelView.updateProperties();
            propertyPanelViewModel.onCanvasChanged();
            notifyObservers();
        }

        public void selectSingleObject(GraphicObjectViewModel object) {
            deselectAllObjects();
            selectedObjects.add(object);
            notifyObservers();
        }

        public void selectObjectsInArea(int startX, int startY, int endX, int endY) {
            selectedObjects.clear(); // Clear previous selections

            // Calculate the selection bounds
            int minX = Math.min(startX, endX);
            int minY = Math.min(startY, endY);
            int maxX = Math.max(startX, endX);
            int maxY = Math.max(startY, endY);
            java.awt.Rectangle selectionArea = new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);

            // Select objects that intersect with the selection area
            for (GraphicObjectViewModel objectViewModel : graphicObjects) {
                java.awt.Rectangle objectBounds = new java.awt.Rectangle(
                        objectViewModel.getX(),
                        objectViewModel.getY(),
                        objectViewModel.getWidth(),
                        objectViewModel.getHeight()
                );

                // Check if the selection area intersects the object bounds
                if (selectionArea.intersects(objectBounds)) {
                    selectedObjects.add(objectViewModel);
                }
            }

            notifyObservers(); // Notify observers about the selection change
        }

        public void moveSelectedObjects(int deltaX, int deltaY) {
            for (GraphicObjectViewModel object : selectedObjects) {
                object.move(object.getX() + deltaX, object.getY() + deltaY);
            }
            notifyObservers();
        }

        public void deselectAllObjects() {
            selectedObjects.clear();
            notifyObservers();
        }

        public TextObject findTextObjectAt(int x, int y) {
            for (GraphicObjectViewModel objectViewModel : graphicObjects) {
                // Access the wrapped graphic object inside GraphicObjectViewModel
                GraphicObject graphicObject = objectViewModel.getGraphicObject();

                // Check if the graphic object is a TextObject
                if (graphicObject instanceof TextObject) {
                    TextObject textObject = (TextObject) graphicObject;

                    // Check if (x, y) falls within the bounds of the textObject
                    if (x >= textObject.getX() && x <= textObject.getX() + textObject.getWidth() &&
                            y >= textObject.getY() && y <= textObject.getY() + textObject.getHeight()) {
                        return textObject;
                    }
                }
            }
            return null; // Return null if no TextObject is found at (x, y)
        }

        public void updateTextObject(TextObject textObject) {
            notifyObservers(); // Refresh the canvas after text change
        }

        // 좌표에 있는 도형을 찾아 반환하는 메서드
        GraphicObjectViewModel findObjectAt(int x, int y) {
            for (GraphicObjectViewModel object : graphicObjects) {
                if (x >= object.getX() && x <= object.getX() + object.getWidth() &&
                        y >= object.getY() && y <= object.getY() + object.getHeight()) {
                    return object;
                }
            }
            return null;
        }
        public void updateSelectedObject(int x, int y, int width, int height) {
            if (selectedObject != null) {
                selectedObject.move(x, y);
                selectedObject.resize(width, height);
                notifyObservers(); // 변경 사항 알림
            }
        }


    }
