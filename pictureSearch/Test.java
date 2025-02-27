import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
    	PictureList pictureList=new PictureList("picture-normal.data.txt");
    	for(int i=0; i<pictureList.size();i++) {
    		Picture pic=pictureList.get(i);
    		pic.print();	
    	}
	}
	

}
