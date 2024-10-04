package view;

import viewmodel.PropertyPanelViewModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyPanelView extends JPanel {
    private PropertyPanelViewModel propertyPanelViewModel;
    private JLabel positionLabel;
    private JLabel sizeLabel;
    private JTextField xField;
    private JTextField yField;
    private JTextField widthField;
    private JTextField heightField;
    private JComboBox<String> shapeSelector;
    private JButton confirmButton;
    private JButton cancelButton;

    public PropertyPanelView(PropertyPanelViewModel propertyPanelViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 도형 타입을 선택하는 콤보박스 추가
        String[] shapes = {"Rectangle", "Ellipse", "Line", "Text"};
        shapeSelector = new JComboBox<>(shapes);

        positionLabel = new JLabel("Position (X, Y):");
        sizeLabel = new JLabel("Size (Width, Height):");

        xField = new JTextField(5);
        yField = new JTextField(5);
        widthField = new JTextField(5);
        heightField = new JTextField(5);


        // 하단에 확인 버튼과 취소 버튼 추가
        confirmButton = new JButton("확인");
        cancelButton = new JButton("취소");


        this.add(new JLabel("Select Shape:"));
        this.add(shapeSelector);
        this.add(positionLabel);
        this.add(xField);
        this.add(yField);
        this.add(sizeLabel);
        this.add(widthField);
        this.add(heightField);
        this.add(confirmButton);
        this.add(cancelButton);

        // 입력 필드의 변경이 발생할 때 이벤트 처리
        registerFieldEvents();
        registerShapeSelectorEvents();
        registerButtonEvents();
    }

    // 필드 값이 변경되었을 때 ViewModel에 반영
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
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers for the position and size.");
                }
            }
        };

        xField.addActionListener(listener);
        yField.addActionListener(listener);
        widthField.addActionListener(listener);
        heightField.addActionListener(listener);
    }

    // 콤보박스에서 선택된 도형을 ViewModel에 반영
    private void registerShapeSelectorEvents() {
        shapeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedShape = (String) shapeSelector.getSelectedItem();
                // 선택된 도형에 따라 ViewModel을 통해 새로운 도형을 생성하거나 설정
                propertyPanelViewModel.setSelectedShapeType(selectedShape);
            }
        });
    }
    // 확인 및 취소 버튼의 이벤트 처리
    private void registerButtonEvents() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 확인 버튼 클릭 시 도형의 위치와 크기 값을 설정
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    propertyPanelViewModel.updatePositionAndSize(x, y, width, height);
                    JOptionPane.showMessageDialog(null, "도형이 설정되었습니다.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "유효한 값을 입력하세요.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 취소 버튼 클릭 시 입력 필드를 초기화
                xField.setText("");
                yField.setText("");
                widthField.setText("");
                heightField.setText("");
                JOptionPane.showMessageDialog(null, "설정이 취소되었습니다.");
            }
        });
    }

    // 선택된 객체의 속성 정보를 ViewModel에서 가져와서 화면에 표시
    public void updateProperties() {
        String properties = propertyPanelViewModel.getObjectProperties();
        String[] parts = properties.split(",");

        xField.setText(parts[0].split(":")[1].trim());
        yField.setText(parts[1].split(":")[1].trim());
        widthField.setText(parts[2].split(":")[1].trim());
        heightField.setText(parts[3].split(":")[1].trim());

        // 선택된 도형에 따라 콤보박스 업데이트
        String selectedShape = propertyPanelViewModel.getSelectedShapeType();
        shapeSelector.setSelectedItem(selectedShape);
    }
}
