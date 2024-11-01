package view;

import viewmodel.CanvasViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasView extends JPanel {
    private CanvasViewModel canvasViewModel;

    public CanvasView(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;

        // 마우스 이벤트 처리 등록
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasViewModel.handleMousePressed(e.getX(), e.getY(), e.getButton());
                SwingUtilities.getWindowAncestor(CanvasView.this).repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                canvasViewModel.handleMouseReleased(e.getX(), e.getY());
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                canvasViewModel.handleMouseDragged(e.getX(), e.getY());
                repaint();  // 드래그 중에는 캔버스를 계속 다시 그리기
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvasViewModel.render(g);  // ViewModel에서 그리기 작업을 위임
    }

    // 캔버스를 다시 그리기 위한 메서드
    public void updateCanvas() {

        repaint();  // 캔버스를 다시 그리도록 요청
    }

    public void registerEvents() {
        // 필요한 추가 이벤트 핸들러가 있으면 여기서 등록
    }
}
