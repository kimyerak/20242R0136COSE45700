import model.Ellipse;
import model.Line;
import model.Rectangle;
import model.TextObject;
import view.MainView;
import view.PropertyPanelView;
import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;

public class Main {
    public static void main(String[] args) {
        PropertyPanelViewModel propertyPanelViewModel = new PropertyPanelViewModel();
        PropertyPanelView propertyPanelView = new PropertyPanelView(propertyPanelViewModel);
        CanvasViewModel canvasViewModel = new CanvasViewModel(propertyPanelViewModel, propertyPanelView);

        propertyPanelViewModel.setCanvasViewModel(canvasViewModel);

        canvasViewModel.addObserver(propertyPanelView);

        canvasViewModel.addGraphicObject(new Rectangle(50, 50, 100, 100));
        canvasViewModel.addGraphicObject(new Ellipse(200, 100, 150, 80));
        canvasViewModel.addGraphicObject(new TextObject(300, 200, "미리디 화이팅"));
        canvasViewModel.addGraphicObject(new Line(100, 300, 200, 400));

        MainView mainView = new MainView(canvasViewModel, propertyPanelView ,propertyPanelViewModel);

        mainView.updateView();
    }
}