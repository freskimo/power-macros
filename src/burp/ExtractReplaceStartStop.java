package burp;

public class ExtractReplaceStartStop extends ExtractReplaceMethod {
    private String startString;
    private String stopString;


    public ExtractReplaceStartStop(Extraction extraction, String startString, String stopString){
        super(extraction);
        this.startString = startString;
        this.stopString = stopString;
    }
    public ExtractReplaceStartStop(Replace replace, String startString, String stopString){
        super(replace);
        this.startString = startString;
        this.stopString = stopString;
    }

    @Override
    public String getExtractionString(String requestResponse) {
            String ret = "EXTRACTION_ERROR";
            int index_of_start = requestResponse.indexOf(this.startString);

            if (index_of_start >= 0) {
                String tmp_part = requestResponse.substring(index_of_start + startString.length());

                int index_of_stop = tmp_part.indexOf(this.stopString);

                if (index_of_stop >= 0) {
                    ret = tmp_part.substring(0, index_of_stop);
                }
            }
            return ret;
    }
}
