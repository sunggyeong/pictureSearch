package pictureSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PictureList {
    private ArrayList<Picture> pictureList;

    public PictureList(String infoFileName) {
        pictureList = new ArrayList<>();
        try {
            File file = new File(infoFileName);
            Scanner input = new Scanner(file);

            System.out.println("Loading picture info from: " + file.getAbsolutePath());

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("//")) {
                    try {
                        Picture picture = new Picture(line);
                        pictureList.add(picture);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error parsing picture info: " + line);
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + infoFileName);
        }
    }

    public int size() {
        return pictureList.size();
    }

    public Picture get(int i) {
        return pictureList.get(i);
    }

    public void add(Picture pic) {
        pictureList.add(pic);
    }

	public void remove(Picture picture) {
		pictureList.remove(picture);
		
	}
   
    
}
