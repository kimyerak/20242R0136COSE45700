//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello world!");
//    }
//}

import view.MainView;
import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;
import viewmodel.GraphicObjectViewModel;

public class Main {
    public static void main(String[] args) {
        // ViewModel 인스턴스 생성
        CanvasViewModel canvasViewModel = new CanvasViewModel();
        PropertyPanelViewModel propertyPanelViewModel = new PropertyPanelViewModel();

        // 초기 그래픽 객체 추가 (예시로 간단히 몇 개 추가)
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(new model.Rectangle(50, 50, 100, 100)));
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(new model.Ellipse(200, 100, 150, 80)));
        canvasViewModel.addGraphicObject(new GraphicObjectViewModel(new model.TextObject(300, 200, "Hello World!")));

        // MainView 인스턴스를 생성하고 ViewModel을 주입
        MainView mainView = new MainView(canvasViewModel, propertyPanelViewModel);

        // 애플리케이션 실행 후, 필요 시 View 업데이트
        // mainView.updateView();  // 사용자가 상호작용할 때 적절히 호출 가능
    }
}
