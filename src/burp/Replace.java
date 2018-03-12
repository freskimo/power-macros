package burp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/7/16.
 */
public class Replace extends ExtractReplace {
    private boolean urlDecode = false;
    private Map<String, Extraction> linkedExtracts = new HashMap<>();

    public Replace(){ }

    public void addLinkedExtraction(Extraction ext) {
        if(!this.getLinkedExtractMap().containsKey(ext.getId())){
            linkedExtracts.put(ext.getId(), ext);
        }
    }
    public void addLinkedExtractions(List<Extraction> extList){
        for (Extraction ext: extList) {
            this.addLinkedExtraction(ext);
        }
    }

    public Map<String, Extraction> getLinkedExtractMap() {
        return linkedExtracts;
    }
    public ArrayList<Extraction> getLinkedExtractList() {
        ArrayList<Extraction> rtnList = new ArrayList<>();
        rtnList.addAll(getLinkedExtractMap().values());
        return rtnList;
    }
    public void setLinkedExtracts(List<Extraction> linkedExtracts) {
        this.addLinkedExtractions(linkedExtracts);
    }

    public Replace(String name, TransformTypes type, String[] typeArgs, List<Extraction> extList) {
        super(name, type);
        this.addLinkedExtractions(extList);
        this.setExtractReplaceMethod(this, this.getType(), typeArgs);
    }

    public Replace(String name, String type, String[] typeArgs, List<Extraction> extList) {
        this(name, TransformTypes.valueOf(type), typeArgs, extList);
    }


    public String replaceData(IHttpRequestResponse request){
        return this.replaceExtractions(new String(request.getRequest()),
                        BurpExtender.getInstance().helpers
        );
    }

//    public GlobalExtractionModel getGlobalExtractions() {
//        return globalExtractions;
//    }
//    public void setGlobalExtractions(GlobalExtractionModel globalExtractions) {
//        this.globalExtractions = globalExtractions;
//    }
    public boolean isUrlDecode() {
        return urlDecode;
    }
    public void setUrlDecode(boolean urlDecode) { this.urlDecode = urlDecode; }

    @Override
    public String toString() { return ""; }

    public String replaceExtractions(String request, IExtensionHelpers helpers) {
        for (Extraction extraction: this.getLinkedExtractMap().values()) {
//            BurpExtender.getInstance().stdout.println("\nExtraction type: " + extraction.getTypeString());

//            if (extraction.getTypeString().equals(TransformTypes.REGEX.text())) {
//                BurpExtender.getInstance().stdout.println("Extraction string: " + extraction.getReplacedExtraction(request));
//                BurpExtender.getInstance().stdout.println("Replacement string: " + this.linkedReplacement.getExtractReplaceMethod().getReplacedExtraction(request));
//                BurpExtender.getInstance().stdout.println("-------------------------------\n");
               request = request.replace(extraction.getExtractionString(request),
                       this.getExtractReplaceMethod().getReplacedExtraction(request));

//            }
        }

        return request;
    }

}
