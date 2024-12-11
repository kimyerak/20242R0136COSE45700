package view;

import viewmodel.CanvasViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasView extends JPanel {
    private CanvasViewModel canvasViewModel;

    private Point dragStartPoint;

    public CanvasView(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvasViewModel.handleMousePressed(e.getX(), e.getY(), e.getButton());
                canvasViewModel.selectObjectAt(e.getX(), e.getY());
                SwingUtilities.getWindowAncestor(CanvasView.this).repaint();

                dragStartPoint = e.getPoint();

                if (canvasViewModel.hasSelectedObjects()) {
                    canvasViewModel.setDragStart(dragStartPoint.x, dragStartPoint.y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                canvasViewModel.handleMouseReleased(e.getX(), e.getY());

                if (!canvasViewModel.hasSelectedObjects()) {
                    canvasViewModel.deselectAllObjects();
                }

                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                canvasViewModel.handleMouseDrag(e.getX(), e.getY());

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        canvasViewModel.render(g);

    }
}
