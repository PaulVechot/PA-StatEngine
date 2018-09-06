package models;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ConfigurationSet {
    private String label;
    private int id;
    private ArrayList<Condition> conditions = new ArrayList<>();
    private ArrayList<Association> associations = new ArrayList<>();
    private ArrayList<SelectedData> selectedData = new ArrayList<>();
    private ArrayList<DataSourceInfo> dataSources = new ArrayList<>();

    public ConfigurationSet(JsonObject configuration,
                            DataSourceInfo[] dataSources) {
        this.label = configuration.get("label").getAsString();
        this.dataSources.addAll(Arrays.asList(dataSources));

        JsonArray conditionsArray = configuration.get("conditions")
                                                 .getAsJsonArray();
        for (int i = 0; i < conditionsArray.size(); i++) {
            JsonObject condition = conditionsArray.get(i).getAsJsonObject();
            this.conditions.add(new Condition(condition));
        }

        JsonArray associationsArray = configuration.get("associations")
                                                   .getAsJsonArray();
        for (int i = 0; i < associationsArray.size(); i++) {
            JsonObject association = associationsArray.get(i).getAsJsonObject();
            this.associations.add(new Association(association));
        }

        JsonArray selectedDataArray = configuration.get("selected_data")
                                                   .getAsJsonArray();
        for (int i = 0; i < selectedDataArray.size(); i++) {
            JsonObject selectedData =
                    selectedDataArray.get(i).getAsJsonObject();
            this.selectedData.add(new SelectedData(selectedData));
        }
    }

    public ConfigurationSet(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    public ArrayList<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(ArrayList<Association> associations) {
        this.associations = associations;
    }

    public ArrayList<DataSourceInfo> getDataSources() {
        return dataSources;
    }

    public void setDataSources(ArrayList<DataSourceInfo> dataSources) {
        this.dataSources = dataSources;
    }

    public ArrayList<SelectedData> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(ArrayList<SelectedData> selectedData) {
        this.selectedData = selectedData;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return this.id;
    }

    public String[] getDataSourceLabels() {
        String[] dataSourceLabels = new String[dataSources.size()];

        for (int i = 0; i < dataSources.size(); i++) {
            dataSourceLabels[i] = dataSources.get(i).getLabel();
        }

        return dataSourceLabels;
    }
}
