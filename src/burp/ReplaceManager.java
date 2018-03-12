package burp;

import java.util.ArrayList;
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



    public static void addReplace(Replace rep) {
        if(!getRepModelMap().containsKey(rep.getId())){
            repModelMap.put(rep.getId(), rep);
//            burpAction = new BurpAction(rep);
//            BurpExtender.getInstance().callbacks.registerSessionHandlingAction(burpAction);
        }
    }

    public static ArrayList<Replace> getReplacementList() {
        ArrayList<Replace> rtnList = new ArrayList<>();
        rtnList.addAll(getRepModelMap().values());
        return rtnList;
    }

}
