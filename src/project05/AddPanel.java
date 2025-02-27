package project05;

import javax.swing.*;
import pictureSearch.Picture;
import pictureSearch.Stuff;
import pictureSearch.StuffList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddPanel extends JFrame implements ActionListener {
    private static final long serialVersionUID = -4911243125153381529L;
    private JTextField timeField = new JTextField(25);
    private JTextField tagField = new JTextField(25);
    private JTextField commentField = new JTextField(30);
    private JTextField imagePathField = new JTextField(30);

    private JPanel stuffPanelContainer = new JPanel();
    private ArrayList<StuffPanel> stuffPanels = new ArrayList<>();
    
    private JButton selectButton = new JButton("Select Image File");
    private JButton moreStuff = new JButton("More Stuff");
    private JButton okInput = new JButton("OK-INPUT END");
    
    private PictureSection pictureSection;

    public AddPanel(PictureSection pictureSection) {
        this.pictureSection = pictureSection;
        setTitle("Add a Picture");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // BorderLayout 사용

        // Time and Picture Input Panel
        JPanel p1 = new JPanel();
        p1.add(new JLabel("Time"));
        p1.add(timeField);
        p1.add(new JLabel("(Picture)"));
        p1.add(tagField);
        add(p1, BorderLayout.NORTH);

        // Select Image File Button and StuffPanel
        stuffPanelContainer.setLayout(new GridLayout(1, 1));
        stuffPanelContainer.add(new StuffPanel());
        stuffPanels.add((StuffPanel) stuffPanelContainer.getComponent(0));

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(selectButton, BorderLayout.WEST);
        p2.add(stuffPanelContainer, BorderLayout.CENTER);

        add(p2, BorderLayout.CENTER);

        // Comments and Action Buttons
        JPanel p3 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        p3.add(new JLabel("Comments"), gbc);
        p3.add(commentField, gbc);
        gbc.gridy = 1; // 다음 요소는 새로운 행에 추가
        p3.add(moreStuff, gbc);
        p3.add(okInput, gbc);

        add(p3, BorderLayout.SOUTH);

        selectButton.addActionListener(new SelectButtonListener());
        moreStuff.addActionListener(new MoreStuffButtonListener());
        okInput.addActionListener(this);
    }

    private class SelectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePathField.setText(selectedFile.getAbsolutePath());
            }
        }

    }

    private class MoreStuffButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            StuffPanel newStuffPanel = new StuffPanel();
            stuffPanels.add(newStuffPanel);
            stuffPanelContainer.setLayout(new GridLayout(stuffPanels.size(), 1));
            stuffPanelContainer.add(newStuffPanel);
            revalidate();
            repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String inputTime = timeField.getText();
        if (inputTime.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");
            inputTime = LocalDateTime.now().format(formatter);
        }
        String inputTag = tagField.getText();
        String inputComment = commentField.getText();
        String inputImagePath = imagePathField.getText();
        
        Picture picture = new Picture();
        picture.setPictureId("m_"+inputTime);
        picture.setTimeStamp(inputTime);
        picture.setImageInfo("IMG"+inputTime);
        picture.setImageTag(inputTag);
        picture.setImageComment(inputComment);
        picture.setImagePath(inputImagePath);

        // 모든 StuffPanel의 정보를 picture에 추가
        for (StuffPanel sp : stuffPanels) {
            String inputStuffType = sp.getStuffTypeField().getText();
            String inputStuffName = sp.getStuffNameField().getText();
            String inputStuffTags = sp.getStuffTagsField().getText();
            
            StuffList.addIfNonExistent(inputStuffType, inputStuffName, inputStuffTags);
            String stuffId=StuffList.getStuffID(inputStuffName);
            
            Stuff stuff = new Stuff();
            stuff.setStuffId(stuffId);
            stuff.setStuffType(inputStuffType);
            stuff.setStuffName(inputStuffName);
            stuff.setStuffTags(inputStuffTags);
            picture.setPictureStuffs(stuff);
        }

        PicturePanel picturePanel = new PicturePanel(picture, inputImagePath);
        pictureSection.addPicturePanel(picturePanel);
        pictureSection.getPictureList().add(picture);
        dispose();
    }
}
