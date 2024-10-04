package viewmodel;

public interface Event {
    void handle(int x, int y);  // 이벤트를 처리하는 메서드, 좌표를 받음
}
