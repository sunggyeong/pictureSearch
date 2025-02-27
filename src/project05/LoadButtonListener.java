package project05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class LoadButtonListener implements ActionListener {
    private PictureSection pictureSection;

    public LoadButtonListener(PictureSection pictureSection) {
        this.pictureSection = pictureSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            PictureSection newPictureSection = new PictureSection(selectedFile.getAbsolutePath());
            newPictureSection.setVisible(true);
            pictureSection.dispose();  // 현재 창을 닫고 새 창을 엽니다.
        }
    }
}

