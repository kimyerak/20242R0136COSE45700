package view;
import viewmodel.CanvasObserver;
import viewmodel.PropertyPanelViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyPanelView extends JPanel implements CanvasObserver {
    private PropertyPanelViewModel propertyPanelViewModel;

    private JLabel objectTypeLabel;  // 선택된 객체의 타입 표시
    private JTextField xField;
    private JTextField yField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField textField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JButton selectButton;

    public PropertyPanelView(PropertyPanelViewModel propertyPanelViewModel) {
        this.propertyPanelViewModel = propertyPanelViewModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 객체 타입 레이블
        objectTypeLabel = new JLabel("No object selected");
        this.add(objectTypeLabel);

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

        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Text:"));
        textPanel.add(textField);

        confirmButton = new JButton("확인");
        cancelButton = new JButton("취소");
        selectButton = new JButton("선택");

        JPanel buttonPanel_1 = new JPanel();
        buttonPanel_1.add(confirmButton);
        buttonPanel_1.add(cancelButton);

        JPanel buttonPanel_2 = new JPanel();
        buttonPanel_2.add(selectButton);

        // 필드 및 버튼 추가
        this.add(positionPanel);
        this.add(sizePanel);
        this.add(textPanel);
        this.add(buttonPanel_1);
        this.add(buttonPanel_2);

        registerButtonEvents();
        updateProperties();  // 초기 상태 설정
    }
    @Override
    public void onCanvasChanged() {
        updateProperties();
    }

    // 속성 패널을 뷰모델의 데이터로 업데이트
    public void updateProperties() {

            String selectedType = propertyPanelViewModel.getSelectedShapeType();
            System.out.println("클릭된 properties: " + selectedType);

            if (!selectedType.equals("No object selected")) {
                System.out.println("UpdateProperties에서 case1");
                objectTypeLabel.setText(selectedType);
                objectTypeLabel.revalidate();
                objectTypeLabel.repaint();
                xField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectX()));
                yField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectY()));
                widthField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectWidth()));
                heightField.setText(String.valueOf(propertyPanelViewModel.getSelectedObjectHeight()));
                if (selectedType.equals("TextObject")) {
                    textField.setEnabled(true);
                    textField.setText(propertyPanelViewModel.getText());
                } else {
                    textField.setEnabled(false);
                    textField.setText("");
                }
            } else {
                System.out.println("UpdateProperties에서 case2");
                objectTypeLabel.setText("No object selected");
                xField.setText("");
                yField.setText("");
                widthField.setText("");
                heightField.setText("");
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
                    propertyPanelViewModel.updatePositionAndSize(x, y, width, height);

                    if (textField.isEnabled()) {
                        propertyPanelViewModel.updateText(textField.getText());
                    }

                    JOptionPane.showMessageDialog(null, "Object updated.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid values.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProperties(); // 새로운 메서드 호출
                JOptionPane.showMessageDialog(null, "Canceled.");
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProperties(); // 새로운 메서드 호출
            }
        });
    }
}
