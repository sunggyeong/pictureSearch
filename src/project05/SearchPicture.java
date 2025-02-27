package project05;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pictureSearch.Picture;
import pictureSearch.Stuff;

public class SearchPicture extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8984174031724957327L;
	private Border lineBorder = BorderFactory.createLineBorder(Color.BLUE, 1); // 파란색 경계선 정의
    private JTextField timeFrom = new JTextField(10);
    private JTextField timeTo = new JTextField(10);
    private JTextField imageTagSearch = new JTextField(10);
    private JTextField imageCommentSearch = new JTextField(10);
    private StuffPanel stuffSearch = new StuffPanel();
    private JTextField generalSearch = new JTextField(10);
    private JButton andSearch = new JButton("AND SEARCH");
    private JButton orSearch = new JButton("OR SEARCH");
    private JButton close = new JButton("CLOSE");
    private PictureSection pictureSection;

    public SearchPicture(PictureSection pictureSection) {
        this.pictureSection = pictureSection;

        // 시간 검색을 위한 패널 설정
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3)); // 2x3 그리드 레이아웃 설정
        p1.setBorder(BorderFactory.createTitledBorder(lineBorder, "Time Search")); // 경계선과 제목 설정
        p1.add(new JLabel("From"));
        p1.add(timeFrom); // 입력 필드 추가
        p1.add(new JLabel("(yyyy-MM-dd_HH:mm:ss)"));
        p1.add(new JLabel("To"));
        p1.add(timeTo); // 입력 필드 추가

        // 키워드 검색을 위한 패널 설정
        JPanel p2 = new JPanel(new BorderLayout()); // BorderLayout 사용
        p2.setBorder(BorderFactory.createTitledBorder(lineBorder, "Keyword Search")); // 경계선과 제목 설정

        JPanel p21 = new JPanel(new GridLayout(2, 2)); // 2x2 그리드 레이아웃 설정
        p21.add(new JLabel("Tags"));
        p21.add(imageTagSearch); // 입력 필드 추가
        p21.add(new JLabel("Comments"));
        p21.add(imageCommentSearch); // 입력 필드 추가
        p2.add(p21, BorderLayout.WEST); // p2 패널의 왼쪽에 p21 패널 추가

        p2.add(stuffSearch, BorderLayout.CENTER); // p2 패널의 중앙에 p22 패널 추가

        JPanel p23 = new JPanel(new GridLayout(1, 2)); // 1x2 그리드 레이아웃 설정
        p23.add(new JLabel("General Search"));
        p23.add(generalSearch); // 입력 필드 추가
        p2.add(p23, BorderLayout.SOUTH); // p2 패널의 아래쪽에 p23 패널 추가

        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new GridLayout(2, 1)); // 2x1 그리드 레이아웃 설정
        mainPanel.add(p1); // 시간 검색 패널 추가
        mainPanel.add(p2); // 키워드 검색 패널 추가

        // 버튼 패널 설정
        JPanel buttons = new JPanel();
        buttons.add(andSearch);
        buttons.add(orSearch);
        buttons.add(close);

        // JFrame 설정
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER); // 메인 패널 중앙에 추가
        add(buttons, BorderLayout.SOUTH); // 버튼 패널 아래에 추가

        setTitle("Search Picture"); // 제목 설정
        setSize(600, 300); // 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
        setLocationRelativeTo(null); // 프레임을 화면 중앙에 배치

        andSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime from = null, to = null;
                try {
                    if (!timeFrom.getText().isEmpty()) {
                        from = LocalDateTime.parse(timeFrom.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    }
                    if (!timeTo.getText().isEmpty()) {
                        to = LocalDateTime.parse(timeTo.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                String tags = imageTagSearch.getText();
                String comments = imageCommentSearch.getText();
                String stuffType = stuffSearch.getStuffTypeField().getText();
                String stuffName = stuffSearch.getStuffNameField().getText();
                String stuffTags = stuffSearch.getStuffTagsField().getText();
                String general = generalSearch.getText();

                Picture searchPicture = new Picture();
                if (!tags.isEmpty()) {
                    searchPicture.setImageTag(tags);
                }
                if (!comments.isEmpty()) {
                    searchPicture.setImageComment(comments);
                }
                
                // 항상 Stuff 객체 생성
                Stuff searchStuff = new Stuff();
                searchStuff.setStuffType(stuffType);
                searchStuff.setStuffName(stuffName);
                searchStuff.setStuffTags(stuffTags);
                
                searchPicture.setPictureStuffs(new ArrayList<>());
                searchPicture.getPictureStuffs().add(searchStuff);

                pictureSection.searchANDUpdate(searchPicture, from, to, general);
                dispose();
            }
            
        });

        orSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime from = null, to = null;
                try {
                    if (!timeFrom.getText().isEmpty()) {
                        from = LocalDateTime.parse(timeFrom.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    }
                    if (!timeTo.getText().isEmpty()) {
                        to = LocalDateTime.parse(timeTo.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                String tags = imageTagSearch.getText();
                String comments = imageCommentSearch.getText();
                String stuffType = stuffSearch.getStuffTypeField().getText();
                String stuffName = stuffSearch.getStuffNameField().getText();
                String stuffTags = stuffSearch.getStuffTagsField().getText();
                String general = generalSearch.getText();

                Picture searchPicture = new Picture();
                if (!tags.isEmpty()) {
                    searchPicture.setImageTag(tags);
                }
                if (!comments.isEmpty()) {
                    searchPicture.setImageComment(comments);
                }
                
                // 항상 Stuff 객체 생성
                Stuff searchStuff = new Stuff();
                searchStuff.setStuffType(stuffType);
                searchStuff.setStuffName(stuffName);
                searchStuff.setStuffTags(stuffTags);
                
                searchPicture.setPictureStuffs(new ArrayList<>());
                searchPicture.getPictureStuffs().add(searchStuff);

                pictureSection.searchORUpdate(searchPicture, from, to, general);
                dispose();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}


