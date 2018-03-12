package powermacros.extract;

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



    public static Extraction getExtraction(int i) {
        ArrayList<Extraction> extractions = getExtractionList();
        if (i >= 0 && i < extractions.size()) {
            return extractions.get(i);
        }
        return null;
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
    public static Extraction getExtractionById(String id) {
        return getExtModelMap().get(id);
    }

}
