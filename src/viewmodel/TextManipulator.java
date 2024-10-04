package viewmodel;

public class TextManipulator implements Manipulator {
    private Event downClickEvent;
    private Event dragEvent;
    private Event upClickEvent;

    public TextManipulator(Event downClickEvent, Event dragEvent, Event upClickEvent) {
        this.downClickEvent = downClickEvent;
        this.dragEvent = dragEvent;
        this.upClickEvent = upClickEvent;
    }

    @Override
    public void downClick(int x, int y) {
        System.out.println("Text down click");
        downClickEvent.handle(x, y);
    }

    @Override
    public void drag(int x, int y) {
        System.out.println("Text drag");
        dragEvent.handle(x, y);
    }

    @Override
    public void upClick(int x, int y) {
        System.out.println("Text up click");
        upClickEvent.handle(x, y);
    }
}
