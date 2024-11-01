package viewmodel;

public class LineManipulator implements Manipulator {
    private Event downClickEvent;
    private Event dragEvent;
    private Event upClickEvent;


    @Override
    public void downClick(int x, int y) {
        System.out.println("Line down click");
        downClickEvent.handle(x, y);
    }

    @Override
    public void drag(int x, int y) {
        System.out.println("Line drag");
        dragEvent.handle(x, y);
    }

    @Override
    public void upClick(int x, int y) {
        System.out.println("Line up click");
        upClickEvent.handle(x, y);
    }
}

