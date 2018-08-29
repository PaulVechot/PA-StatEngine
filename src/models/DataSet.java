package models;

import java.util.ArrayList;

public class DataSet {
    private String label;
    private String dataType;
    private ArrayList<String> values = new ArrayList<>();

    public DataSet(String label, String dataType) {
        this.label = label;
        this.dataType = dataType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void addValue(String value) {
        this.values.add(value);
    }

}
