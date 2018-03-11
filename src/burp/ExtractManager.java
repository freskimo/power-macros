package burp;

import java.util.HashMap;
import java.util.Map;

public class ExtractManager {
    private static Map<String, Extraction> repModelMap = new HashMap<>();


    public static Map<String, Extraction> getRepModelMap() {
        return repModelMap;
    }

    public static void setRepModelMap(Map<String, Extraction> repModelMap) {
        ExtractManager.repModelMap = repModelMap;
    }



}
