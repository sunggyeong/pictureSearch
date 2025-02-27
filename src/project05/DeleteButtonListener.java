package project05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    private PictureSection pictureSection;

    public DeleteButtonListener(PictureSection pictureSection) {
        this.pictureSection = pictureSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pictureSection.deleteSelectedPictures();
    }
}
