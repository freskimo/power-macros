package burp;

/**
 * Created by fruh on 9/7/16.
 */
public class Replace extends  ExtractReplace{
    private boolean urlDecode = false;
    private ExtractionModel extractionModel;

    public Replace(String name, TransformTypes type, String[] typeArgs, Extraction extList[]) {
        super(name, type);
        extractionModel = new ExtractionModel(this, extList);
        this.setExtractReplaceMethod(this, this.getType(), typeArgs);
    }
    public Replace(String name, String type, String[] typeArgs, Extraction extList[]) {
        this(name, TransformTypes.valueOf(type), typeArgs, extList);
    }


    public String replaceData(IHttpRequestResponse request){
        return this.extractionModel.replaceExtractions(new String(request.getRequest()),
                        BurpExtender.getInstance().helpers
        );
    }

    public ExtractionModel getExtractionModel() {
        return extractionModel;
    }
    public void setExtractionModel(ExtractionModel extractionModel) {
        this.extractionModel = extractionModel;
    }
    public boolean isUrlDecode() {
        return urlDecode;
    }
    public void setUrlDecode(boolean urlDecode) { this.urlDecode = urlDecode; }

    @Override
    public String toString() { return ""; }


}
