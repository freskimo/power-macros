package powermacros.replace;

import burp.BurpExtender;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import powermacros.transforms.TransformTypes;
import powermacros.transforms.ExtractReplace;
import powermacros.extract.Extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/7/16.
 */
public class Replace extends ExtractReplace {
    private boolean urlDecode = false;
    public ExtractionLink linkedExtracts = new ExtractionLink();
    public Replace(){ }

    public void addLinkedExtraction(Extraction ext) {
        this.linkedExtracts.add(ext);
    }

    public void addLinkedExtractions(List<Extraction> extList){
        for (Extraction ext: extList) {
            this.addLinkedExtraction(ext);
        }
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
    @Override
    public String toString() { return ""; }

    public String replaceExtractions(String request, IExtensionHelpers helpers) {
        for (Extraction extraction: this.linkedExtracts.getLinkedExtractMap().values()) {
               request = request.replace(extraction.getExtractionString(request),
                       this.getExtractReplaceMethod().getReplacedExtraction(request));
        }
        return request;
    }

    public boolean isUrlDecode() {
        return urlDecode;
    }
    public void setUrlDecode(boolean urlDecode) { this.urlDecode = urlDecode; }

}
