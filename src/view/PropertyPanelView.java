package view;

import model.command.BringToFrontCommand;
import model.command.ClearCommand;
import model.command.SendToBackCommand;
import viewmodel.CanvasObserver;
import viewmodel.PropertyPanelViewModel;
import model.GraphicObjectComposite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyPanelView extends JPanel implements CanvasObserver {
    private PropertyPanelViewModel propertyPanelViewModel;

    private JLabel objectTypeLabel;
    private JTextField xField;
    private JTextField yField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField textField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton bringToFrontButton;
    private JButton sendToBackButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;

    public PropertyPanelView(PropertyPanelViewModel propertyPanelViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        objectTypeLabel = new JLabel("No object selected");
        this.add(objectTypeLabel);

        xField = new JTextField(5);
        yField = new JTextField(5);
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        textField = new JTextField(10);

        JPanel positionPanel = new JPanel();
        positionPanel.add(new JLabel("X:"));
        positionPanel.add(xField);
        positionPanel.add(new JLabel("Y:"));
        positionPanel.add(yField);

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Width:"));
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("Height:"));
        sizePanel.add(heightField);

        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Text:"));
        textPanel.add(textField);

        confirmButton = new JButton("확인");
        cancelButton = new JButton("취소");

        JPanel buttonPanel_1 = new JPanel();
        buttonPanel_1.add(confirmButton);
        buttonPanel_1.add(cancelButton);

        bringToFrontButton = new JButton("Bring To Front");
        sendToBackButton = new JButton("Send To Back");

        JPanel buttonPanel_2 = new JPanel();
        buttonPanel_2.add(bringToFrontButton);
        buttonPanel_2.add(sendToBackButton);

        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        JPanel buttonPanel_3 = new JPanel();
        buttonPanel_3.add(undoButton);
        buttonPanel_3.add(redoButton);

        clearButton = new JButton("Clear All Objects");

        JPanel buttonPanel_4 = new JPanel();
        buttonPanel_4.add(clearButton);

        this.add(positionPanel);
        this.add(sizePanel);
        this.add(textPanel);
        this.add(buttonPanel_1);
        this.add(buttonPanel_2);
        this.add(buttonPanel_3);
        this.add(buttonPanel_4);

        registerButtonEvents();
        updateProperties();
    }
    @Override
    public void onCanvasChanged() {
        updateProperties();
    }

    public void updateProperties() {
        String selectedType = propertyPanelViewModel.getSelectedShapeType();

        if (selectedType.equals("Multiple objects")) {
            objectTypeLabel.setText("Multiple objects selected");
            xField.setText("");
            xField.setEnabled(false);
            yField.setText("");
            yField.setEnabled(false);
            widthField.setText("");
            widthField.setEnabled(false);
            heightField.setText("");
            heightField.setEnabled(false);
            textField.setText("");
            textField.setEnabled(false);
        } else if (!selectedType.equals("No object selected")) {
            objectTypeLabel.setText(selectedType);
            objectTypeLabel.revalidate();
            objectTypeLabel.repaint();

            xField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectX()));
            yField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectY()));
            widthField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectWidth()));
            heightField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectHeight()));

            if (selectedType.equals("TextObject")) {
                textField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectText()));
                xField.setEnabled(false);
                yField.setEnabled(false);
                widthField.setEnabled(false);
                heightField.setEnabled(false);
                textField.setEnabled(true);
            } else {
                xField.setEnabled(true);
                yField.setEnabled(true);
                widthField.setEnabled(true);
                heightField.setEnabled(true);
                textField.setText("");
                textField.setEnabled(false);
            }
        } else {
            objectTypeLabel.setText("No object selected");
            xField.setText("");
            xField.setEnabled(false);
            yField.setText("");
            yField.setEnabled(false);
            widthField.setText("");
            widthField.setEnabled(false);
            heightField.setText("");
            heightField.setEnabled(false);
            textField.setText("");
            textField.setEnabled(false);
        }
    }

    private void registerButtonEvents() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    String text = textField.getText();
                    propertyPanelViewModel.updatePositionAndSize(x, y, width, height, text);

                    JOptionPane.showMessageDialog(null, "Object updated.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid values.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProperties();
                JOptionPane.showMessageDialog(null, "Canceled.");
            }
        });

        bringToFrontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicObjectComposite selectedComposite = propertyPanelViewModel.getSelectedObjects();
                if (selectedComposite != null) {
                    BringToFrontCommand command = new BringToFrontCommand(
                            propertyPanelViewModel.getCanvasViewModel(), selectedComposite
                    );
                    propertyPanelViewModel.getCanvasViewModel().executeCommand(command);
                }
            }
        });

        sendToBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicObjectComposite selectedComposite = propertyPanelViewModel.getSelectedObjects();
                if (selectedComposite != null) {
                    SendToBackCommand command = new SendToBackCommand(
                            propertyPanelViewModel.getCanvasViewModel(), selectedComposite
                    );
                    propertyPanelViewModel.getCanvasViewModel().executeCommand(command);
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyPanelViewModel.getCanvasViewModel().undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyPanelViewModel.getCanvasViewModel().redo();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClearCommand clearCommand = new ClearCommand(propertyPanelViewModel.getCanvasViewModel());
                propertyPanelViewModel.getCanvasViewModel().executeCommand(clearCommand);
            }
        });
    }
}
