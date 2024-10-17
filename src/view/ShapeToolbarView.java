// 파일 위치: src/view/ShapeToolbarView.java
package view;

import model.ShapeIcon;
import viewmodel.PropertyPanelViewModel;

import javax.swing.*;
import java.awt.*;

public class ShapeToolbarView extends JPanel {
    private PropertyPanelViewModel propertyPanelViewModel;

    public ShapeToolbarView(PropertyPanelViewModel propertyPanelViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 각 버튼 생성과 아이콘 설정
        JButton clickButton = createShapeButton("Click", Color.BLACK);
        JButton lineButton = createShapeButton("Line", Color.BLUE);
        JButton rectangleButton = createShapeButton("Rectangle", Color.RED);
        JButton ellipseButton = createShapeButton("Ellipse", Color.GREEN);
        JButton textButton = createShapeButton("Text", Color.MAGENTA);

        // 패널에 버튼 추가
        this.add(clickButton);
        this.add(lineButton);
        this.add(rectangleButton);
        this.add(ellipseButton);
        this.add(textButton);
    }

    private JButton createShapeButton(String shapeType, Color color) {
        JButton button = new JButton(new ShapeIcon(20, 20, color, shapeType));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> selectShape(shapeType));
        return button;
    }

    private void selectShape(String shapeType) {
        propertyPanelViewModel.setSelectedShapeType(shapeType);
    }
}
