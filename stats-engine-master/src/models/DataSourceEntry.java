package models;

import java.util.Hashtable;

import com.google.gson.Gson;

public class DataSourceEntry {
    private Hashtable<String, DataSourceField> fields;

    public DataSourceEntry(Hashtable<String, DataSourceField> fields) {
        this.fields = fields;
    }

    public Hashtable<String, DataSourceField> getFields() {
        return fields;
    }

    public void setFields(Hashtable<String, DataSourceField> fields) {
        this.fields = fields;
    }

    /**
     * Adds passed field to this entry's fields, returning false on overwrite
     * @param field The field to add to this entry's fields
     * @return false if a field by this name already existed, true otherwise
     */
    public boolean addField(DataSourceField field) {
        return this.fields.put(field.getName(), field) == null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
