package view;

import viewmodel.CanvasViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasView extends JPanel {
    private CanvasViewModel canvasViewModel;

    private Point dragStartPoint;
    private Rectangle selectionRectangle;

    public CanvasView(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;

        // 마우스 이벤트 처리 등록
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                canvasViewModel.handleMousePressed(e.getX(), e.getY(), e.getButton());
                // 선택된 객체 전달!!!!!!!!!!!!!!!
                canvasViewModel.selectObjectAt(e.getX(), e.getY());
                SwingUtilities.getWindowAncestor(CanvasView.this).repaint();

                dragStartPoint = e.getPoint();


                if (canvasViewModel.hasSelectedObjects()) {
                    canvasViewModel.setDragStart(dragStartPoint.x, dragStartPoint.y);
                } else {
                    selectionRectangle = new Rectangle();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                canvasViewModel.handleMouseReleased(e.getX(), e.getY());

                if (selectionRectangle != null) {
                    canvasViewModel.selectObjectsInArea(dragStartPoint.x, dragStartPoint.y, e.getX(), e.getY());
                    selectionRectangle = null;
                } else {
                    if (!canvasViewModel.hasSelectedObjects()) {
                        canvasViewModel.deselectAllObjects();
                    }
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectionRectangle != null) {
                    int x = Math.min(dragStartPoint.x, e.getX());
                    int y = Math.min(dragStartPoint.y, e.getY());
                    int width = Math.abs(dragStartPoint.x - e.getX());
                    int height = Math.abs(dragStartPoint.y - e.getY());

                    selectionRectangle = new Rectangle(x, y, width, height);
                    canvasViewModel.selectObjectsInArea(dragStartPoint.x, dragStartPoint.y, e.getX(), e.getY());
                } else if (canvasViewModel.hasSelectedObjects()) {
                    int deltaX = e.getX() - dragStartPoint.x;
                    int deltaY = e.getY() - dragStartPoint.y;
                    canvasViewModel.moveSelectedObjects(deltaX, deltaY);

                    dragStartPoint = e.getPoint();
                }

                repaint();  // 드래그 중에는 캔버스를 계속 다시 그리기
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvasViewModel.render(g);  // ViewModel에서 그리기 작업을 위임

        if (selectionRectangle != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(selectionRectangle);
        }
    }

    // 캔버스를 다시 그리기 위한 메서드
    public void updateCanvas() {

        repaint();  // 캔버스를 다시 그리도록 요청
    }

    public void registerEvents() {
        // 필요한 추가 이벤트 핸들러가 있으면 여기서 등록
    }
}
