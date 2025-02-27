package project05;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import pictureSearch.Picture;
import pictureSearch.Stuff;

public class PicturePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Picture picture;
    private String imagePath;
    private boolean selected=false;

    public PicturePanel(Picture picture, String imagePath) {
        this.picture = picture;
        this.imagePath = imagePath;

        // 이미지 파일의 절대 경로 확인
        File imageFile = new File(imagePath);  // 절대 경로 사용
        if (!imageFile.exists()) {
            System.err.println("Image not found: " + imageFile.getAbsolutePath());
        }

        // 이미지 크기 조정
        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
        Image image = icon.getImage(); // ImageIcon을 Image 객체로 변환
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 원하는 크기로 조정 (200x200)
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 이미지를 BorderLayout.WEST에 추가
        add(imageLabel, BorderLayout.WEST);

        // 정보 패널을 생성하고 태그, 타임스탬프 및 코멘트를 추가
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        JLabel tagLabel = new JLabel(picture.getImageTag());
        JLabel timestampLabel = new JLabel(picture.getTimeStamp() != null ? picture.getTimeStamp().toString() : "No timestamp");
        JLabel commentLabel = new JLabel(picture.getImageComment());

        infoPanel.add(timestampLabel);
        infoPanel.add(tagLabel);
        add(commentLabel, BorderLayout.SOUTH);

        add(infoPanel, BorderLayout.NORTH);

        // Stuff 정보를 담을 패널 생성
        JPanel stuffPanelContainer = new JPanel();
        stuffPanelContainer.setLayout(new BoxLayout(stuffPanelContainer, BoxLayout.Y_AXIS));
        if (picture.getPictureStuffs().isEmpty()) {
            stuffPanelContainer.add(new StuffPanel()); // 빈 StuffPanel 추가
        } else {
            for (Stuff stuff : picture.getPictureStuffs()) {
                StuffPanel stuffPanel = new StuffPanel(stuff);
                stuffPanelContainer.add(stuffPanel);
            }
        }
        add(stuffPanelContainer, BorderLayout.CENTER);
        setBorder(new LineBorder(Color.BLACK, 1));
     // MouseListener 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = !selected;
                setBorder(selected ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
            }
        });
    }

    public Picture getPicture() {
        return picture;
    }
    public boolean isSelected() {
        return selected;
    }
}





