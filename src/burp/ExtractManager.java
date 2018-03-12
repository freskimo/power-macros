package burp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExtractManager {
    private static Map<String, Extraction> extModelMap = new HashMap<>();

    public static ArrayList<Extraction> getExtractionList() {
        ArrayList<Extraction> rtnList = new ArrayList<>();
        rtnList.addAll(getExtModelMap().values());
        return rtnList;
    }
    public static Map<String, Extraction> getExtModelMap() {
        return extModelMap;
    }
    public static void setExtModelMap(Map<String, Extraction> extModelMap) {
        ExtractManager.extModelMap = extModelMap;
    }





    public static void addExtraction(Extraction ext) {
        if(!getExtModelMap().containsKey(ext.getId())){
            extModelMap.put(ext.getId(), ext);
        }
    }
    public static void addExtractions(Extraction extList[]){
        for (Extraction ext: extList) {
            addExtraction(ext);
        }
    }


}
