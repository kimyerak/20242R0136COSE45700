# 디자인 패턴을 적용한 벡터 그래픽 에디터 (개발중)
### Observer + Composite + State + Command Pattern 적용 🍀

| 디자인 패턴      | 설명                                                                                                                                               |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| **Observer Pattern**  | `CanvasViewModel`의 상태가 바뀔 때, `PropertyPanelView`와 같은 다른 뷰에 상태 변화가 자동으로 반영되는 구조                               |
| **Composite Pattern** | 여러 개의 도형 객체 (`Rectangle`, `Ellipse`, 등)을 `CanvasViewModel` 안에서 동일한 방식으로 관리하고 트리 구조를 이루는 부분              |
| **State Pattern** | 여러 개의 마우스 이벤트 (`downClick`, `upClick`, `drag`등)             |
| **Command Pattern** | Undo, Redo가 편하게 Stack에 X,Y,Width,Height를 `push`, `pop`              |

### MVVM 폴더구조💗
이 프로젝트는 **MVVM** (Model-View-ViewModel) 패턴을 사용하여 구조화되었으며, `model`, `view`, `viewmodel`의 세 가지 주요 폴더로 나뉩니다.
```
src
├── model
│   ├── Ellipse
│   ├── GraphicObject
│   ├── ImageObject
│   ├── Line
│   ├── Picture
│   ├── Rectangle
│   ├── Shape
│   ├── ShapeIcon
│   └── TextObject
├── view
│   ├── CanvasView
│   ├── MainView
│   ├── PropertyPanelView
│   └── ShapeToolbarView
└── viewmodel
    ├── CanvasObserver
    ├── CanvasViewModel
    ├── DownClickEvent
    ├── DragEvent
    ├── Event
    ├── GraphicObjectViewModel
    ├── LineManipulator
    ├── Manipulator
    ├── PropertyPanelViewModel
    ├── TextManipulator
    └── UpClickEvent
```
### canvas object 🖥️
- image
- text
- line
- rectangle
- ellipse

## 기능 ⚙️
- object 를 type 별로 생성 할 수 있다 ✔️
- object를 multi select 할 수 있다 ✔️
- object가 선택된 경우 속성창에 선택된 object 의 속성을 보여줄 수 있다 ✔️
- 선택된 object 가 없는 경우 아무 것도 보여주지 않는다 ✔️
- object 의 위치와 크기를 조절 할 수 있다 ✔️
- 속성창에 위치와 크기 값이 즉시 반영 된다 🔺(위치는 ok, 크기는 아직 개발중)
- 속성창에서 특정 값을 바꾸면 object 에 즉시 반영 된다 ✔️
- object 의 z-order 를 조절할 수 있다 ❌
