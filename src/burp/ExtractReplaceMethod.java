package burp;

public abstract class ExtractReplaceMethod {
    protected Extraction extraction;
    protected Replace replace;
    protected TransformTypes type;

    public ExtractReplaceMethod(Extraction extraction){
        this.type = extraction.getType();
        this.extraction = extraction;
    }
    public ExtractReplaceMethod(Replace replace){
        this.type = replace.getType();
        this.replace = replace;
    }

    public abstract String getExtractionString(String requestResponse);
    public String doReplace(){
        return "";
    }
}
