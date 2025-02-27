package pictureSearch;

import java.util.ArrayList;

public class StuffList {
    private static ArrayList<Stuff> stuffList = new ArrayList<>();
    private static int count = 0; // 현재 저장된 stuff의 개수
    private static int nextId = 1; // 다음에 할당할 ID

    public StuffList() {
    }

    public static void addStuff(Stuff stuff) {
        String currentName = stuff.getStuffName();
        boolean found = false;

        // 기존 사물들의 이름과 비교하여 중복 검사
        for (int i = 0; i < count; i++) {
            if (stuffList.get(i).getStuffName().equals(currentName)) {
                // 기존 사물의 ID를 새로운 사물에 부여
                stuff.setStuffId(stuffList.get(i).getStuffId());
                found = true;
                break;
            }
        }

        // 새로운 사물이라면 ID 부여 후 리스트에 추가
        if (!found) {
            stuff.setStuffId(formatStuffId(nextId));
            nextId++;
        }

        // stuff 배열에 추가
        stuffList.add(stuff);
        count++;
    }

    // ID를 00000001 형식으로 변환
    private static String formatStuffId(int id) {
        return String.format("%08d", id);
    }

    // 현재 등록된 모든 사물 정보들을 콘솔로 출력
    public static void print() {
        for (int i = 0; i < count; i++) {
            stuffList.get(i).stuffPrint();
            System.out.println(" ");
        }
    }

    // 주어진 이름과 타입에 해당하는 stuffID를 반환, 없으면 null 반환
    public static String getStuffID(String name) {
        for (Stuff stuff : stuffList) {
            if (stuff.getStuffName().equals(name)) {
                return stuff.getStuffId();
            }
        }
        return null;
    }

    // 주어진 이름과 타입에 해당하는 stuff가 존재하지 않으면 추가
    public static void addIfNonExistent(String type, String name, String tags) {
        String id = getStuffID(name);
        if (id == null) {
            Stuff newStuff = new Stuff();
            newStuff.setStuffType(type);
            newStuff.setStuffName(name);
            newStuff.setStuffTags(tags);
            addStuff(newStuff);
        }
    }
}

