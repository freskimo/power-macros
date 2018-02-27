package burp;

import java.util.ArrayList;


public class BurpActionModel {
    private ArrayList<BurpAction> actionList;

    private BurpExtender burpExtender;
    public BurpActionModel(BurpExtender burpExtender){
        this.burpExtender = burpExtender;
    }
}
