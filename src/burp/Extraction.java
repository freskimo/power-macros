package burp;

import java.util.Set;

/**
 * Created by fruh on 9/7/16.
 */
public class Extraction extends ExtractReplace {
    private Set<String> repRefSet;

    public Extraction(String name, TransformTypes type, String typeArgs[]){
        super(name, type);
        setExtractReplaceMethod(this, this.getType(), typeArgs);
    }
    public Extraction(String name, String type, String typeArgs[]){
        super(name, TransformTypes.valueOf(type));
        setExtractReplaceMethod(this, this.getType(), typeArgs);
    }


    public String getExtractionString(String request){
        return this.getExtractReplaceMethod().getReplacedExtraction(request);
    }

    @Override
    public String toString() { return "extract TOSTRING"; }

}
