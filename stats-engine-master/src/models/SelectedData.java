package models;

import com.google.gson.JsonObject;

public class SelectedData {
    private String field;
    private String operation;

    public SelectedData(String field, String operation) {
        this.field = field;
        this.operation = operation;
    }

    public SelectedData(JsonObject selectedData) {
        this.field = selectedData.get("field").getAsString();
        this.operation = selectedData.get("operation").getAsString();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFieldName() {
        return field.substring(field.indexOf(".") + 1);
    }
}
