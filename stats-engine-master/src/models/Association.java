package models;

import com.google.gson.JsonObject;

public class Association {
    private String field1;
    private String field2;

    public Association(JsonObject association) {
        this.field1 = association.get("field1").getAsString();
        this.field2 = association.get("field2").getAsString();
    }

    public Association(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
