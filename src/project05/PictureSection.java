package project05;

import javax.swing.*;
import pictureSearch.PictureList;
import pictureSearch.Picture;
import pictureSearch.Stuff;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PictureSection extends JFrame {
    private static final long serialVersionUID = 7165553339370634695L;
    private JButton allPictures = new JButton("Show All Pictures");
    private JButton addButton = new JButton("ADD");
    private JButton deleteButton = new JButton("DELETE");
    private JButton loadButton = new JButton("LOAD");
    private JButton saveButton = new JButton("SAVE");
    private JButton searchButton = new JButton("SEARCH");
    private ArrayList<PicturePanel> list = new ArrayList<>();
    private PictureList pictureList;
    private String infoFileName;
    private JPanel contentPanel = new JPanel();

    public PictureSection(String infoFileName) {
        this.infoFileName = infoFileName;
        setTitle("Simple Picture Search");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        try {
            pictureList = new PictureList(infoFileName);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating PictureList: " + e.getMessage());
            e.printStackTrace();
        }

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(5, 1));
        buttons.add(addButton);
        buttons.add(deleteButton);
        buttons.add(loadButton);
        buttons.add(saveButton);
        buttons.add(searchButton);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // 세로로 PicturePanel들을 나열
        JScrollPane scrollPane = new JScrollPane(contentPanel); // 스크롤 가능한 패널

        for (int i = 0; i < pictureList.size(); i++) {
            PicturePanel p = new PicturePanel(pictureList.get(i), "src/project05/" + pictureList.get(i).getImagePath());
            list.add(p);
            contentPanel.add(p);
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(allPictures, BorderLayout.NORTH);
        mainPanel.add(buttons, BorderLayout.EAST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Add button action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPanel addPictureFrame = new AddPanel(PictureSection.this);
                addPictureFrame.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		SearchPicture searchPicture=new SearchPicture(PictureSection.this);
        		searchPicture.setVisible(true);
        	}
        });

        loadButton.addActionListener(new LoadButtonListener(this));
        saveButton.addActionListener(new SaveButtonListener(this));
        deleteButton.addActionListener(new DeleteButtonListener(this));
        allPictures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContentPanel();
            }
        });
    }

    public String getInfoFileName() {
        return infoFileName;
    }

    public void setPictureList(PictureList pictureList) {
        this.pictureList = pictureList;
    }

    public void updateContentPanel() {
        contentPanel.removeAll();
        list.clear();
        for (int i = 0; i < pictureList.size(); i++) {
            PicturePanel p = new PicturePanel(pictureList.get(i), "src/project05/"+pictureList.get(i).getImagePath());
            list.add(p);
            contentPanel.add(p);
        }
        list.sort(Comparator.comparing(p -> p.getPicture().getTimeStamp())); // 타임스탬프 기준으로 정렬
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void addPicturePanel(PicturePanel picturePanel) {
        list.add(picturePanel);
        contentPanel.add(picturePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public PictureList getPictureList() {
        return pictureList;
    }
    public ArrayList<PicturePanel> getPicturePanels() {
        return list;
    }

    private boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public void searchANDUpdate(Picture searchPicture, LocalDateTime from, LocalDateTime to, String general) {
        ArrayList<PicturePanel> newList = new ArrayList<>();
        for (PicturePanel picturePanel : list) {
            Picture picture = picturePanel.getPicture();
            boolean match = true;

            if (from != null && (picture.getTimeStamp() == null || picture.getTimeStamp().isBefore(from))) {
                match = false;
            }
            if (to != null && (picture.getTimeStamp() == null || picture.getTimeStamp().isAfter(to))) {
                match = false;
            }
            if (!isStringNullOrEmpty(searchPicture.getImageTag()) && (picture.getImageTag() == null || !picture.getImageTag().equals(searchPicture.getImageTag()))) {
                match = false;
            }
            if (!isStringNullOrEmpty(searchPicture.getImageComment()) && (picture.getImageComment() == null || !picture.getImageComment().equals(searchPicture.getImageComment()))) {
                match = false;
            }
            for (Stuff searchStuff : searchPicture.getPictureStuffs()) {
                if (isStringNullOrEmpty(searchStuff.getStuffType()) && isStringNullOrEmpty(searchStuff.getStuffName()) && isStringNullOrEmpty(searchStuff.getStuffTags())) {
                    continue;
                }
                boolean stuffMatch = picture.getPictureStuffs().stream().anyMatch(s -> 
                    (isStringNullOrEmpty(searchStuff.getStuffType()) || (s.getStuffType() != null && s.getStuffType().equals(searchStuff.getStuffType()))) &&
                    (isStringNullOrEmpty(searchStuff.getStuffName()) || (s.getStuffName() != null && s.getStuffName().equals(searchStuff.getStuffName()))) &&
                    (isStringNullOrEmpty(searchStuff.getStuffTags()) || (s.getStuffTags() != null && s.getStuffTags().equals(searchStuff.getStuffTags())))
                );
                if (!stuffMatch) {
                    match = false;
                    break;
                }
            }

            if (!isStringNullOrEmpty(general) && !(picture.getImageTag() != null && picture.getImageTag().equals(general)) &&
                !(picture.getImageComment() != null && picture.getImageComment().equals(general)) &&
                !picture.getPictureStuffs().stream().anyMatch(s -> (s.getStuffType() != null && s.getStuffType().equals(general)) ||
                        (s.getStuffName() != null && s.getStuffName().equals(general)) || (s.getStuffTags() != null && s.getStuffTags().equals(general)))) {
                match = false;
            }

            if (match) {
                newList.add(picturePanel);
            }
        }

        list = newList;
        updateContentPanelWithList();
    }


    public void searchORUpdate(Picture searchPicture, LocalDateTime from, LocalDateTime to, String general) {
        ArrayList<PicturePanel> newList = new ArrayList<>();
        for (PicturePanel picturePanel : list) {
            Picture picture = picturePanel.getPicture();
            boolean match = false;

            // 타임스탬프 조건
            if (from != null && picture.getTimeStamp() != null && !picture.getTimeStamp().isBefore(from)) {
                match = true;
            } else if (to != null && picture.getTimeStamp() != null && !picture.getTimeStamp().isAfter(to)) {
                match = true;
            }

            // 태그와 코멘트 조건 (정확히 일치)
            if (!isStringNullOrEmpty(searchPicture.getImageTag()) && picture.getImageTag() != null && picture.getImageTag().equals(searchPicture.getImageTag())) {
                match = true;
            } else if (!isStringNullOrEmpty(searchPicture.getImageComment()) && picture.getImageComment() != null && picture.getImageComment().equals(searchPicture.getImageComment())) {
                match = true;
            }

            // Stuff 조건 (정확히 일치)
            for (Stuff searchStuff : searchPicture.getPictureStuffs()) {
                if (isStringNullOrEmpty(searchStuff.getStuffType()) && isStringNullOrEmpty(searchStuff.getStuffName()) && isStringNullOrEmpty(searchStuff.getStuffTags())) {
                    continue;
                }
                boolean stuffMatch = picture.getPictureStuffs().stream().anyMatch(s -> 
                    (!isStringNullOrEmpty(searchStuff.getStuffType()) && s.getStuffType() != null && s.getStuffType().equals(searchStuff.getStuffType())) ||
                    (!isStringNullOrEmpty(searchStuff.getStuffName()) && s.getStuffName() != null && s.getStuffName().equals(searchStuff.getStuffName())) ||
                    (!isStringNullOrEmpty(searchStuff.getStuffTags()) && s.getStuffTags() != null && s.getStuffTags().equals(searchStuff.getStuffTags()))
                );
                if (stuffMatch) {
                    match = true;
                    break;
                }
            }

            // General 조건 (정확히 일치)
            if (!isStringNullOrEmpty(general)) {
                if ((picture.getImageTag() != null && picture.getImageTag().equals(general)) ||
                    (picture.getImageComment() != null && picture.getImageComment().equals(general)) ||
                    picture.getPictureStuffs().stream().anyMatch(s -> 
                        (s.getStuffType() != null && s.getStuffType().equals(general)) ||
                        (s.getStuffName() != null && s.getStuffName().equals(general)) ||
                        (s.getStuffTags() != null && s.getStuffTags().equals(general))
                    )) {
                    match = true;
                }
            }

            if (match) {
                newList.add(picturePanel);
            }
        }

        list = newList;
        updateContentPanelWithList();
    }



    private void updateContentPanelWithList() {
        contentPanel.removeAll();
        for (PicturePanel p : list) {
            contentPanel.add(p);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public void deleteSelectedPictures() {
        ArrayList<PicturePanel> toRemove = new ArrayList<>();
        for (PicturePanel picturePanel : list) {
            if (picturePanel.isSelected()) {
                toRemove.add(picturePanel);
            }
        }

        if (toRemove.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a picture to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        list.removeAll(toRemove);
        for (PicturePanel picturePanel : toRemove) {
            contentPanel.remove(picturePanel);
            pictureList.remove(picturePanel.getPicture());
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}



