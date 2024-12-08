package view;

import model.TextObject;
import viewmodel.CanvasViewModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasView extends JPanel {
    private CanvasViewModel canvasViewModel;

    private JTextField textEditor;
    private TextObject editingTextObject;
    private boolean isEditing = false;

    private Point dragStartPoint;
    private Rectangle selectionRectangle;

    public CanvasView(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;

        this.setLayout(null);

        textEditor = new JTextField();
        textEditor.setVisible(false);
        textEditor.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        textEditor.setBackground(getBackground());
        textEditor.setForeground(Color.BLACK);
        textEditor.addActionListener(e -> finalizeTextEdit());
        textEditor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                finalizeTextEdit();
            }
        });

        textEditor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextEditorSize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextEditorSize();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextEditorSize();
            }
        });

        add(textEditor);

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

                if (e.getClickCount() == 2) {
                    TextObject clickedTextObject = canvasViewModel.findTextObjectAt(e.getX(), e.getY());
                    if (clickedTextObject != null) {
                        startTextEdit(clickedTextObject);
                        canvasViewModel.deselectAllObjects();
                    }
                } else {
                    finalizeTextEdit();
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
                    canvasViewModel.handleMouseDrag(e.getX(), e.getY());
                }

                repaint();  // 드래그 중에는 캔버스를 계속 다시 그리기
            }
        });
    }

    private void startTextEdit(TextObject textObject) {
        editingTextObject = textObject;
        textEditor.setText(textObject.getText());

        Font font = getFont();
        textEditor.setFont(font);

        FontMetrics metrics = getGraphics().getFontMetrics(font);
        int textWidth = metrics.stringWidth(textObject.getText());
        int textHeight = metrics.getHeight();

        textEditor.setBounds(textObject.getX() - 1, textObject.getY() - metrics.getAscent() + 13, textWidth + 6, textHeight + 2);
        textEditor.setVisible(true);
        textEditor.requestFocus();

        isEditing = true;
        repaint();
    }

    private void finalizeTextEdit() {
        if (isEditing && editingTextObject != null) {
            editingTextObject.setText(textEditor.getText());
            canvasViewModel.updateTextObject();
            textEditor.setVisible(false);
            editingTextObject = null;
            isEditing = false;
            repaint();
        }
    }

    private void updateTextEditorSize() {
        FontMetrics metrics = textEditor.getFontMetrics(textEditor.getFont());
        int newWidth = metrics.stringWidth(textEditor.getText()) + 6;
        int height = textEditor.getHeight();

        textEditor.setSize(newWidth, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 배경을 초기화 (화면 클리어)
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // 기존 상태 저장
        Stroke originalStroke = g2d.getStroke();
        Color originalColor = g2d.getColor();

        // 드래그 중일 때만 selectionRectangle을 그린다
        if (selectionRectangle != null && selectionRectangle.width > 0 && selectionRectangle.height > 0) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1)); // Width 1의 선 설정
            g2d.draw(selectionRectangle); // Selection Rectangle 그리기
        }

        // Stroke와 Color를 원래 상태로 복원
        g2d.setStroke(originalStroke);
        g2d.setColor(originalColor);

        // 캔버스의 나머지 객체 렌더링
        if (isEditing && editingTextObject != null) {
            canvasViewModel.render(g, editingTextObject);
        } else {
            canvasViewModel.render(g);
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