package models;

import java.util.Hashtable;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DataSourceInfo {
    private String label;
    private String type;
    private Hashtable<String, String> accessInfo = new Hashtable<>();

    public DataSourceInfo(String label, String type) {
        this.label = label;
        this.type = type;
    }

    public DataSourceInfo(JsonObject dataSource) {
        this.label = dataSource.get("label").getAsString();
        this.type = dataSource.get("type").getAsString();

        JsonObject accessInfo = dataSource.get("accessinfo")
                                          .getAsJsonObject();
        Set<String> keys = accessInfo.keySet();

        for (String key : keys) {
            this.accessInfo.put(key, accessInfo.get(key).getAsString());
        }
    }

    /*@Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }*/

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hashtable<String, String> getAccessInfo() {
        return accessInfo;
    }

    public void addAccessInfo(String key, String value) {
        this.accessInfo.put(key, value);
    }
    
    @Override
    public String toString() {
    	return label+":"+type;
    }

}
