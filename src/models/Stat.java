package models;

import java.util.ArrayList;

public class Stat {
    private String label;
    private String representation;
    private ArrayList<DataSet> dataSets = new ArrayList<>();

    public Stat(String label, String representation,
                ArrayList<DataSet> dataSets) {
        this.label = label;
        this.representation = representation;
        this.dataSets = dataSets;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public ArrayList<DataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(ArrayList<DataSet> dataSets) {
        this.dataSets = dataSets;
    }
}
