package burp;

import java.util.HashMap;
import java.util.Map;

public class ReplaceManager {
    private static Map<String, Replace> repModelMap = new HashMap<>();

    public static Map<String, Replace> getRepModelMap() {
        return repModelMap;
    }

    public static void setRepModelMap(Map<String, Replace> repModelMap) {
        ReplaceManager.repModelMap = repModelMap;
    }



}
