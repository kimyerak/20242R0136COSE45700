### 디자인 패턴을 적용한 벡터 그래픽 에디터
- MVVM
### observer + composite pattern 적용
이 프로젝트는 Observer Pattern과 Composite Pattern이 모두 적용된 구조로, 두 패턴을 함께 사용하는 복합적인 디자인이 현재 프로젝트에 적용된 상태입니다.

- Observer Pattern: CanvasViewModel의 상태가 바뀔 때, PropertyPanelView와 같은 다른 뷰에 상태 변화가 자동으로 반영되는 구조.
- Composite Pattern: 여러 개의 도형 객체(Rectangle, Ellipse, 등)를 CanvasViewModel 안에서 동일한 방식으로 관리하고 트리 구조를 이루는 부분.


### canvas object 
- image
- text
- line
- rectangle
- ellipse

## 기능
- object 를 type 별로 생성 할 수 있다
- object를 multi select 할 수 있다
- object가 선택된 경우 속성창에 선택된 object 의 속성을 보여줄 수 있다
- 선택된 object 가 없는 경우 아무 것도 보여주지 않는다
- object 의 위치와 크기를 조절 할 수 있다
- 속성창에 위치와 크기 값이 즉시 반영 된다
- 속성창에서 특정 값을 바꾸면 object 에 즉시 반영 된다
- object 의 z-order 를 조절할 수 있다
