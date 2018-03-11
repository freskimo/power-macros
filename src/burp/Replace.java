package burp;

/**
 * Created by fruh on 9/7/16.
 */
public class Replace extends ExtractReplace {
    private boolean urlDecode = false;
    private LinkedExtractionModel linkedExtracts = new LinkedExtractionModel();


    public Replace(){ }


    public LinkedExtractionModel getLinkedExtracts() {
        return linkedExtracts;
    }
    public void setLinkedExtracts(LinkedExtractionModel linkedExtracts) {
        this.linkedExtracts = linkedExtracts;
    }

    public Replace(String name, TransformTypes type, String[] typeArgs, Extraction extList[]) {
        super(name, type);
        linkedExtracts =  new LinkedExtractionModel(extList);
        this.setExtractReplaceMethod(this, this.getType(), typeArgs);
    }

    public Replace(String name, String type, String[] typeArgs, Extraction extList[]) {
        this(name, TransformTypes.valueOf(type), typeArgs, extList);
    }

    public void addLinkedExtraction(Extraction e){
        linkedExtracts.addExtraction(e);
    }

    public String replaceData(IHttpRequestResponse request){
        return this.replaceExtractions(new String(request.getRequest()),
                        BurpExtender.getInstance().helpers
        );
    }

    public GlobalExtractionModel getGlobalExtractions() {
        return globalExtractions;
    }
    public void setGlobalExtractions(GlobalExtractionModel globalExtractions) {
        this.globalExtractions = globalExtractions;
    }
    public boolean isUrlDecode() {
        return urlDecode;
    }
    public void setUrlDecode(boolean urlDecode) { this.urlDecode = urlDecode; }

    @Override
    public String toString() { return ""; }


}
