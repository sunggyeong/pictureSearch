

public class Image {
	String imageId;
	String imageFileName;
    String imageTag;
	
	Image(String imageinfo){
		String[] tokens=imageinfo.trim().split(";");
		imageId=tokens[0];
		imageFileName=tokens[1];
		imageTag=tokens[2];
	}
}
