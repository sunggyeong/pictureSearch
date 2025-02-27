package pictureSearch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Picture {
    private String pictureId;
    private LocalDateTime timeStamp;
    private String imageInfo;
    private String[] stuffInfos; // Stuff 정보를 배열로 저장
    private ArrayList<Stuff> pictureStuffs = new ArrayList<>();
    private String imageTag;
    private String imageComment;
    private String imagePath;
    
    public Picture() {
    	
    }
 // 주어진 사진 정보로 Picture 객체를 만드는 생성자
    public Picture(String infoLine) {
        try {
            System.out.println("Parsing picture info: " + infoLine);

            // '<'와 '>'를 제거하고 토큰을 분리
            String cleanLine = infoLine.replaceAll("<", ""); // '<' 제거
            String[] tokens = cleanLine.split(">");

            // 토큰에서 필요한 정보 추출 및 할당
            pictureId = tokens[0].trim();
            String[] images = tokens[2].trim().split(";");
            imageInfo = images[0].trim();
            imagePath = images[1].trim();
                        
            
            if (!tokens[3].isEmpty()) {
                stuffInfos = tokens[3].trim().split("\\]\\s*\\[");
                for (String stuffInfo : stuffInfos) {
                    Stuff stuff = new Stuff(stuffInfo);
                    pictureStuffs.add(stuff);
                }
            } else {
                System.out.println("No stuff info for picture: " + pictureId);
            }

            if(tokens.length > 4) {
            	imageTag = tokens[4].trim();
            }
            if(tokens.length > 5) {
                imageComment = tokens[5].trim();
            }
            

            // 타임스탬프 파싱
            if (!tokens[1].trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");
                try {
                    timeStamp = LocalDateTime.parse(tokens[1].trim(), formatter);
                } catch (DateTimeParseException ex) {
                    // 잘못된 타임스탬프 형식일 때 이전에 파싱된 문자열을 출력
                    System.out.println("Wrong DateTime Format: " + tokens[1].trim());
                    timeStamp = null; // 타임스탬프를 null로 설정
                }
            } else {
                // 타임스탬프가 비어 있는 경우에는 null로 처리
                timeStamp = null; // 또는 다른 기본값으로 설정
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing: " + infoLine, e);
        }
    }


    // Picture 객체의 모든 정보를 콘솔에 출력
    public void print() {
        if (timeStamp != null) {
            System.out.print("< " + pictureId + " > < " + timeStamp + " > < " + imageInfo + " >");
            for (Stuff stuff : pictureStuffs) {
                stuff.stuffPrint();
            }
            System.out.println("< " + imageTag + " > < " + imageComment + " >");
        } else {
            System.out.print("< " + pictureId + " > < null > < " + imageInfo + " >");
            for (Stuff stuff : pictureStuffs) {
                stuff.stuffPrint();
            }
            System.out.println("< " + imageTag + " > < " + imageComment + " >");
        }
    }

    // Getter와 Setter 메서드 추가
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");
    	this.timeStamp=LocalDateTime.parse(timeStamp,formatter);
    }
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public ArrayList<Stuff> getPictureStuffs() {
        return pictureStuffs;
    }

    public void setPictureStuffs(ArrayList<Stuff> pictureStuffs) {
        this.pictureStuffs = pictureStuffs;
    }
    public void setPictureStuffs(Stuff stuff) {
    	this.pictureStuffs.add(stuff);
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public String getImageComment() {
        return imageComment;
    }

    public void setImageComment(String imageComment) {
        this.imageComment = imageComment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    @Override
    public String toString() {
        String imagePathToSave = imagePath;
        
        // 이미지 경로에서 "images" 부분을 찾아 그 이전의 경로를 잘라내기
        int imagesIndex = imagePathToSave.lastIndexOf("images");
        int endIndex=imagePathToSave.length();
        if (imagesIndex != -1) {
            imagePathToSave = imagePathToSave.substring(imagesIndex,endIndex);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<").append(pictureId).append(">")
          .append("<").append(timeStamp != null ? timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS")) : "").append(">")
          .append("<").append(imageInfo).append(";").append(imagePathToSave).append(">")
          .append("<");
        for (Stuff stuff : pictureStuffs) {
            sb.append("[").append(stuff.getStuffId()).append(";")
              .append(stuff.getStuffType()).append(";")
              .append(stuff.getStuffName()).append(";")
              .append(stuff.getStuffTags()).append("]");
        }
        sb.append(">")
          .append("<").append(imageTag).append(">")
          .append("<").append(imageComment).append(">");
        return sb.toString();
    }
}

