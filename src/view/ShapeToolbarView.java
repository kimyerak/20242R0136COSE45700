package view;

import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;
import model.ShapeIcon;

import javax.swing.*;
import java.awt.*;

public class ShapeToolbarView extends JPanel {
    private PropertyPanelViewModel propertyPanelViewModel;
    private CanvasViewModel canvasViewModel;

    public ShapeToolbarView(PropertyPanelViewModel propertyPanelViewModel, CanvasViewModel canvasViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.canvasViewModel = canvasViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton clickButton = createShapeButton("Click", Color.BLACK);
        JButton lineButton = createShapeButton("Line", Color.BLUE);
        JButton rectangleButton = createShapeButton("Rectangle", Color.RED);
        JButton ellipseButton = createShapeButton("Ellipse", Color.GREEN);
        JButton textButton = createShapeButton("TextObject", Color.MAGENTA);
        JButton imageButton = createShapeButton("Image", Color.BLACK);

        this.add(clickButton);
        this.add(lineButton);
        this.add(rectangleButton);
        this.add(ellipseButton);
        this.add(textButton);
        this.add(imageButton);
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
        if (shapeType.equals("Image")) {
            canvasViewModel.loadImage();
        } else {
            propertyPanelViewModel.setSelectedShapeType(shapeType);
            canvasViewModel.addGraphicObjectByShapeType(shapeType);
        }
    }
}
