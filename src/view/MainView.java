package view;

import viewmodel.CanvasViewModel;
import viewmodel.PropertyPanelViewModel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private CanvasView canvasView;
    private PropertyPanelView propertyPanelView;
    private ShapeToolbarView shapeToolbarView;

    public MainView(CanvasViewModel canvasViewModel, PropertyPanelView propertyPanelView, PropertyPanelViewModel propertyPanelViewModel) {
        this.setTitle("Vector Graphic Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvasView = new CanvasView(canvasViewModel);
        this.propertyPanelView = propertyPanelView;
        shapeToolbarView = new ShapeToolbarView(propertyPanelViewModel, canvasViewModel);

        this.setLayout(new BorderLayout());
        this.add(canvasView, BorderLayout.CENTER);
        this.add(propertyPanelView, BorderLayout.EAST);
        this.add(shapeToolbarView, BorderLayout.WEST);

        this.setVisible(true);
    }

    public void updateView() {
        propertyPanelView.updateProperties();
        canvasView.repaint();
    }
}
