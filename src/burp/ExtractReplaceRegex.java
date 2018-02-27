package burp;

public class ExtractReplaceRegex extends ExtractReplaceMethod {
    String regexStr;

    public ExtractReplaceRegex(Extraction extraction, String regexStr){
        super(extraction);
        this.regexStr = regexStr;
    }
    public ExtractReplaceRegex(Replace replace, String regexStr){
        super(replace);
        this.regexStr = regexStr;
    }

    @Override
    public String getExtractionString(String requestResponse) {
        return regexStr;
    }
}
