package burp;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/8/16.
 */
public class GlobalReplaceModel extends AbstractTableModel {
    private static List<Replace> replacesLast  = new LinkedList<>();

    private static Map<String, Replace> repModelMap = ReplaceManager.getRepModelMap();
    private static List<Replace> replaces = ReplaceManager.getReplacementList();


    private String[] cols = {"Linked extraction", "Replacement name", "Extraction type", "Replacement type"};
    BurpAction burpAction;

    public GlobalReplaceModel(){
        loadRows();
    }

    public void loadRows(){

        fireTableRowsInserted(replaces.size() - 1, replaces.size() - 1);
//        burpAction = new BurpAction(rep);
//        BurpExtender.getInstance().callbacks.registerSessionHandlingAction(burpAction);
    }


    @Override
    public int getRowCount() {
        return replaces.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int index) {
        if (index < cols.length) {
            return cols[index];
        }
        return null;
    }

    public int getRowById(String id) {
        int row = -1;

        if (replaces.size() > 0) {
            for (row = 0; row < replaces.size(); row++) {
                if (id.equals(replaces.get(row).getId())) {
                    break;
                }
            }
        }
        return row;
    }

    public Replace getReplace(int i) {
        if (i >= 0 && i < replaces.size()) {
            return replaces.get(i);
        }
        return null;
    }

    public Replace getReplaceById(String id) {
        return repModelMap.get(id);
    }

    @Override
    public Object getValueAt(int row, int col) {
        String ret;

        switch (col) {
            case 0:
                if(getReplace(row).getLinkedExtractMap().size() > 1){
                    ret = "[Multiple]";
                }else{
                    ret = getReplace(row).getLinkedExtractList().get(0).getId();
                }
                break;

            case 1:
                ret = String.valueOf(getReplace(row).getId());
                break;

            case 2:
                if(getReplace(row).getLinkedExtractMap().size() > 1){
                    ret = "[Multiple]";
                }else{
                    ret = getReplace(row).getLinkedExtractList().get(0).getTypeString();
                }
                break;

            case 3:
                ret = getReplace(row).getTypeString();
                break;

            default:
                ret = null;
                break;
        }
        return ret;
    }

    public void remove(String id) {
        int row = getRowById(id);
        removeRow(row);
    }

    public void removeRow(int row) {
//        Replace r = replaces.get(row);

//        if (r.getMsgId() != null) {
//            extender.getMessagesModel().getMessageById(r.getMsgId()).getRepRefSet().remove(r.getId());
//        }
//        repModelMap.remove(r.getId());
//        if (replacesLast.contains(r)) {
//            replacesLast.remove(r);
//        }
//        replaces.remove(row);
//
//        fireTableRowsDeleted(row, row);
    }

    public void removeAll() {
        repModelMap.clear();
        replaces.clear();
        replacesLast.clear();

        fireTableDataChanged();
    }

    public void addReplaceLast(Replace rep) {
        replaces.add(rep);
        replacesLast.add(rep);
        repModelMap.put(rep.getId(), rep);

        fireTableRowsInserted(replaces.size() - 1, replaces.size() - 1);
    }


    public List<Replace> getReplacesLast() {
        return replacesLast;
    }

    public Map<String, Replace> getRepModelMap() {
        return repModelMap;
    }

}
