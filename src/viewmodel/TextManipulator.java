package viewmodel;

import model.TextObject;

public class TextManipulator implements Manipulator {
    private Event downClickEvent;
    private Event dragEvent;
    private Event upClickEvent;
    private TextObject textObject;

    public TextManipulator(Event downClickEvent, Event dragEvent, Event upClickEvent, TextObject textObject) {
        this.downClickEvent = downClickEvent;
        this.dragEvent = dragEvent;
        this.upClickEvent = upClickEvent;
        this.textObject = textObject;
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
    // 텍스트 수정
    public void setText(String newText) {
        textObject.setText(newText);
        System.out.println("Text updated to: " + newText);
    }
}
