package burp;

public enum TransformTypes {
    STARTEND("Start/end string"),
    REGEX("Regex"),
    PYTHON("python"),
    JAVASCRIPT("JavaScript");


    private String type;

    TransformTypes(String type) {
        this.type = type;
    }

    public String text() {
        String formattedType = this.type.toLowerCase();
        return formattedType.substring(0,1).toUpperCase() + formattedType.substring(1);
    }

}
