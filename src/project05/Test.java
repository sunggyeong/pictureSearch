package project05;

import javax.swing.SwingUtilities;

public class Test {
    public static void main(String[] args) {
        System.out.println("Starting application...");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PictureSection pictureSection = new PictureSection("C:\\Users\\epni0\\eclipse-workspace\\project05\\src\\project05\\picture-normal.data");
                pictureSection.setVisible(true);
                System.out.println("PictureSection is visible.");
            }
        });
    }
}

