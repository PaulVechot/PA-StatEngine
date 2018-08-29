package business;

import java.util.ArrayList;

import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceEntry;
import models.DataSourceField;

public class ConditionsEngine {

    /**
     * Applies conditions and associations to some entries, returning valid ones
     * @param configSet The configuration set to apply to the entries
     * @param entries The entries to filter according to the configuration set
     * @return An ArrayList with the complying data source entries
     */
    public ArrayList<DataSourceEntry[]>
    applyConditionsAndAssociations(ConfigurationSet configSet,
                                   DataSourceEntry[][] entries) {
        int[] cursors = new int[entries.length];
        ArrayList<DataSourceEntry[]> validEntries = new ArrayList<>();

        do {
            DataSourceEntry[] entriesToCheck =
                    new DataSourceEntry[entries.length];
            for (int i = 0; i < cursors.length; i++) {
                entriesToCheck[i] = entries[i][cursors[i]];
            }

            DataSourceEntry[] valid = checkConditions(configSet,
                                                      entriesToCheck);
            if (valid == null) {
                continue;
            }

            valid = checkAssociations(configSet, valid);

            if (valid != null) {
                validEntries.add(valid);
            }
        } while (advanceCursors(cursors, entries));

        return validEntries;
    }

    private DataSourceEntry[]
            checkAssociations(ConfigurationSet configSet,
                              DataSourceEntry[] entriesToCheck) {
        ArrayList<Association> associations = configSet.getAssociations();

        for (Association association : associations) {
            if (!associationVerifies(association, configSet, entriesToCheck)) {
                return null;
            }
        }

        return entriesToCheck;
    }

    /**
     * Returns whether an association verifies for a set of entries
     * @param association The association to match the entries against
     * @param configSet The configuration set to use for matching
     * @param entriesToCheck The entries to check for association match
     * @return true if the set of entries to check matches, false otherwises
     */
    private boolean associationVerifies(Association association,
                                        ConfigurationSet configSet,
                                        DataSourceEntry[] entriesToCheck) {
        String[] dataSourceLabels = configSet.getDataSourceLabels();

        String fieldId1 = association.getField1();
        String fieldId2 = association.getField2();

        String dataSource1 = fieldId1.split("[.]")[0];
        String fieldName1 = fieldId1.substring(fieldId1.indexOf(".") + 1);
        String dataSource2 = fieldId2.split("[.]")[0];
        String fieldName2 = fieldId2.substring(fieldId2.indexOf(".") + 1);

        int entry1 = stringIndexInArray(dataSource1, dataSourceLabels);
        int entry2 = stringIndexInArray(dataSource2, dataSourceLabels);

        DataSourceField field1 = entriesToCheck[entry1]
                .getFields().get(fieldName1);
        DataSourceField field2 = entriesToCheck[entry2]
                .getFields().get(fieldName2);
        if (executeCondition(field1, field2.getValue(), "=")) {
            return true;
        }

        return false;
    }

    private DataSourceEntry[]
            checkConditions(ConfigurationSet configSet,
                            DataSourceEntry[] entriesToCheck) {
        ArrayList<Condition> conditions = configSet.getConditions();

        for (Condition condition : conditions) {
            if (!conditionVerifies(condition, configSet, entriesToCheck)) {
                return null;
            }
        }

        return entriesToCheck;
    }

    private boolean conditionVerifies(Condition condition,
                                      ConfigurationSet configSet,
                                      DataSourceEntry[] entriesToCheck) {
        String[] dataSourceLabels = configSet.getDataSourceLabels();

        String fieldId = condition.getOpLeft();
        String value = condition.getOpRight();
        String comparison = condition.getComparison();

        String dataSource = fieldId.split("[.]")[0];
        String fieldName = fieldId.substring(fieldId.indexOf(".") + 1);

        int entryToCheckAgainst = stringIndexInArray(dataSource,
                                                     dataSourceLabels);

        DataSourceField field = entriesToCheck[entryToCheckAgainst]
                .getFields().get(fieldName);
        if (executeCondition(field, value, comparison)) {
            return true;
        }

        return false;
    }

    /**
     * Actually executes the condition against passed data, according to types
     * @param field The data source field to check
     * @param value The value to check against
     * @param comparison The comparison operator to use for comparing values
     * @return true if the comparison is true for passed values, false otherwise
     */
    private boolean executeCondition(DataSourceField field, String value,
                                     String comparison) {
        try {
            if (comparison.equals("=")) {
                return field.getValue().equals(value);
            } else if (comparison.equals("<")) {
                if (field.getType() == "number") {
                    int fieldValue = Integer.parseInt(field.getValue());
                    int compValue = Integer.parseInt(value);
                    return fieldValue < compValue;
                }
            } else if (comparison.equals(">")) {
                if (field.getType() == "number") {
                    int fieldValue = Integer.parseInt(field.getValue());
                    int compValue = Integer.parseInt(value);
                    return fieldValue > compValue;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    private int stringIndexInArray(String dataSource,
                                  String[] dataSourceLabels) {
        for (int i = 0; i < dataSourceLabels.length; i++) {
            if (dataSource.equals(dataSourceLabels[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Advances the cursors in a cyclic manner, one after the other, looping
     * @param cursors The array of cursors to advance
     * @param entries The entries the cursors are pointing at
     * @return true if the cursors could be advanced, false at the very end
     */
    private boolean advanceCursors(int[] cursors, DataSourceEntry[][] entries) {
        for (int i = 0; i < cursors.length; i++) {
            if (cursors[i] < entries[i].length - 1) {
                cursors[i] += 1;
                return true;
            } else {
                cursors[i] = 0;
            }
        }
        return false;
    }
}
