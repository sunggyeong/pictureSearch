package pictureSearch;

public class Stuff {
    private String stuffId;
    private String stuffType;
    private String stuffName;
    private String stuffTags;
    private String stuffImageInfo;

    public Stuff() {
    }

    public Stuff(String stuffInfo) {
    	stuffInfo=stuffInfo.replaceAll("]", ""); 
        // 주어진 stuffInfo를 공백으로 분리하여 토큰으로 만듭니다.
        String[] tokens = stuffInfo.trim().split(";");

        // 토큰에서 필요한 정보 추출하여 필드에 할당합니다.
        this.stuffId = tokens[0].trim();

        // Stuff 정보가 없는 경우를 처리합니다.
        if (tokens.length >= 2) {
            this.stuffType = tokens[1].trim();
        } else {
            this.stuffType = ""; // 빈 경우 처리
        }
        if (tokens.length >= 3) {
            this.stuffName = tokens[2].trim();
        } else {
            this.stuffName = ""; // 빈 경우 처리
        }
        if (tokens.length >= 4) {
            this.stuffTags = tokens[3].trim();
            for (int i = 4; i < tokens.length; i++) {
                this.stuffTags += "; " + tokens[i].trim();
            }
        } else {
            this.stuffTags = ""; // 빈 경우 처리
        }

        // StuffList에 자신을 추가합니다.
        StuffList.addStuff(this);
    }


    // Getter 및 Setter 메서드
    public String getStuffId() {
        return stuffId;
    }

    public String getStuffName() {
        return stuffName;
    }

    public String getStuffType() {
        return stuffType;
    }

    public String getStuffTags() {
        return stuffTags;
    }

    public String getStuffImageInfo() {
        return stuffImageInfo;
    }

    public void setStuffId(String id) {
        this.stuffId = id;
    }

    public void setStuffType(String type) {
        this.stuffType = type;
    }

    public void setStuffName(String name) {
        this.stuffName = name;
    }

    public void setStuffTags(String tags) {
        this.stuffTags = tags;
    }

    // 사물 정보 출력
    public void stuffPrint() {
        System.out.print("[" + this.stuffId + "; " + this.stuffType + "; " + this.stuffName + "; " + this.stuffTags);
    }
}