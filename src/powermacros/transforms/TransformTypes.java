package powermacros.transforms;

import java.util.ArrayList;

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
        return formattedType.substring(0, 1).toUpperCase() + formattedType.substring(1);
    }

    public boolean isImplemented() {
        if (this.equals(REGEX) || this.equals(JAVASCRIPT)) {
            return true;
        } else {
            return false;
        }
    }
}
