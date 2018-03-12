package powermacros.transforms;

import powermacros.extract.Extraction;
import powermacros.replace.Replace;
import powermacros.transforms.TransformTypes;

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

    public abstract String getReplacedExtraction(String requestResponse);
    public abstract String getExtractionArgument();
    public String doReplace(){
        return "";
    }
}
