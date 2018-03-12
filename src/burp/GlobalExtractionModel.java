package burp;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/8/16.
 */
public class GlobalExtractionModel extends AbstractTableModel {

    private static List<Extraction> extractions = ExtractManager.getExtractionList();
    private static Map<String, Extraction> mapAllExtracts = ExtractManager.getExtModelMap();


    private String[] cols = {"Name", "Type"};
    private Replace linkedReplacement;



    public GlobalExtractionModel() {

    }
    public GlobalExtractionModel(Replace linkedReplacement) {
        this();
        this.linkedReplacement = linkedReplacement;
    }




    @Override
    public int getRowCount() {
        return extractions.size();
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

    public static void _updateExtraction(Extraction e){

    }

    public static Extraction _getExtraction(int i) {
        if (i >= 0 && i < extractions.size()) {
            return extractions.get(i);
        }
        return null;
    }

    public Extraction getExtraction(int i) {
        if (i >= 0 && i < extractions.size()) {
            return extractions.get(i);
        }
        return null;
    }

    public Extraction getExtractionById(String id) {
        return mapAllExtracts.get(id);
    }



    @Override
    public Object getValueAt(int row, int col) {
        String ret;

        switch (col) {
            case 0:
                ret = getExtraction(row).getId();
                break;

            case 1:
                ret = String.valueOf(getExtraction(row).getTypeString());
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
//        Extraction e = extractions.get(row);
//
//        // remove extraction reference
//        extender.getMessagesModel().getMessageById(e.getMsgId()).getExtRefSet().remove(e.getId());
//
//        for (String r:e.getRepRefSet()) {
//            extender.getReplaceModel().remove(r);
//        }
//        mapAllExtracts.remove(extractions.get(row).getId());
//        extractions.remove(row);
//;
//        fireTableRowsDeleted(row, row);
    }

    public void removeAll() {
//        for (Extraction e:extractions) {
//            for (Message m : extender.getMessagesModel().getMessages()) {
//                m.getExtRefSet().remove(e.getMsgId());
//            }
//        }
//        for (Message m : extender.getMessagesModel().getMessages()) {
//            m.getExtRefSet().clear();
//        }
//        extractions.clear();
//        mapAllExtracts.clear();
//
//        // delete references
//        extender.getReplaceModel().removeAll();
//
//        fireTableDataChanged();
    }

    public int getRowById(String id) {
        int row = -1;

        if (extractions.size() > 0) {
            for (row = 0; row < extractions.size(); row++) {
                if (id .equals(extractions.get(row).getId())) {
                    break;
                }
            }
        }
        return row;
    }


//    public String replaceExtractions(String request, IExtensionHelpers helpers) {
//        for (Extraction extraction: this.extractions) {
////            BurpExtender.getInstance().stdout.println("\nExtraction type: " + extraction.getTypeString());
//
////            if (extraction.getTypeString().equals(TransformTypes.REGEX.text())) {
////                BurpExtender.getInstance().stdout.println("Extraction string: " + extraction.getReplacedExtraction(request));
////                BurpExtender.getInstance().stdout.println("Replacement string: " + this.linkedReplacement.getExtractReplaceMethod().getReplacedExtraction(request));
////                BurpExtender.getInstance().stdout.println("-------------------------------\n");
//                request = request.replace(extraction.getExtractionString(request),
//                        this.linkedReplacement.getExtractReplaceMethod().getReplacedExtraction(request));
//
////            }
//        }
//
//        return request;
//    }


    public Map<String, Extraction> getExtModelMap() {
        BurpExtender.getInstance().stdout.println("From getExtModelMap (size): " + mapAllExtracts.size());
        return mapAllExtracts;
    }

}
