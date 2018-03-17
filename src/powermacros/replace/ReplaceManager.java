package powermacros.replace;

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

    public static ArrayList<Replace> getReplaceList() {
        ArrayList<Replace> rtnList = new ArrayList<>();
        rtnList.addAll(getRepModelMap().values());
        return rtnList;
    }

    public static void putReplace(Replace rep) {
//        if(!getRepModelMap().containsKey(rep.getId())){
            repModelMap.put(rep.getId(), rep);
//            burpAction = new BurpAction(rep);
//            BurpExtender.getInstance().callbacks.registerSessionHandlingAction(burpAction);
//        }else{
//
//        }
    }

    public static ArrayList<Replace> getReplacementList() {
        ArrayList<Replace> rtnList = new ArrayList<>();
        rtnList.addAll(getRepModelMap().values());
        return rtnList;
    }

    public static Replace getReplace(int i) {
        ArrayList<Replace> replaces = getReplacementList();
        if (i >= 0 && i < replaces.size()) {
            return replaces.get(i);
        }
        return null;
    }
    public static Replace getReplaceById(String id) {
        return getRepModelMap().get(id);
    }
}
