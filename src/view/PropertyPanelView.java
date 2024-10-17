package view;

import viewmodel.PropertyPanelViewModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyPanelView extends JPanel {
    private PropertyPanelViewModel propertyPanelViewModel;
    private JTextField xField;
    private JTextField yField;
    private JTextField widthField;
    private JTextField heightField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTextField textField;

    public PropertyPanelView(PropertyPanelViewModel propertyPanelViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 객체 위치 및 크기 설정 필드
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

        confirmButton = new JButton("확인");
        cancelButton = new JButton("취소");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        this.add(positionPanel);
        this.add(sizePanel);
        this.add(buttonPanel);

        registerFieldEvents();
        registerButtonEvents();
    }

    // 필드에 ViewModel 데이터를 반영하는 메서드
    public void updateProperties() {
        String properties = propertyPanelViewModel.getObjectProperties();
        String[] parts = properties.split(",");

        xField.setText(parts[0].split(":")[1].trim());
        yField.setText(parts[1].split(":")[1].trim());
        widthField.setText(parts[2].split(":")[1].trim());
        heightField.setText(parts[3].split(":")[1].trim());
        textField.setText(propertyPanelViewModel.getText());


    }
    private void registerFieldEvents() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    propertyPanelViewModel.updatePositionAndSize(x, y, width, height);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
                }
            }
        };

        xField.addActionListener(listener);
        yField.addActionListener(listener);
        widthField.addActionListener(listener);
        heightField.addActionListener(listener);
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
                    propertyPanelViewModel.updatePositionAndSize(x, y, width, height);
                    JOptionPane.showMessageDialog(null, "Object updated.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid values.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xField.setText("");
                yField.setText("");
                widthField.setText("");
                heightField.setText("");
                JOptionPane.showMessageDialog(null, "Canceled.");
            }
        });
    }
}
