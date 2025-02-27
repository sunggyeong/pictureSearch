package project05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

import pictureSearch.Picture;

public class SaveButtonListener implements ActionListener {
    private PictureSection pictureSection;

    public SaveButtonListener(PictureSection pictureSection) {
        this.pictureSection = pictureSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)) {
                for (PicturePanel picturePanel : pictureSection.getPicturePanels()) {
                    Picture picture = picturePanel.getPicture();
                    writer.write(picture.toString() + "\n");
                }
                System.out.println("Data saved to " + selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}


