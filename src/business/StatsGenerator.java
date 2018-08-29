package business;

import java.util.ArrayList;

import models.AnalysisResult;
import models.ConfigurationSet;
import models.DataSet;
import models.DataSourceEntry;
import models.DataSourceInfo;
import models.SelectedData;
import models.Stat;

public class StatsGenerator {
    public AnalysisResult
    generateAnalysisResult(ConfigurationSet configSet,
                           ArrayList<DataSourceEntry[]> entries) {
        ArrayList<Stat> stats = new ArrayList<>();

        stats.add(generateStat(configSet, "table", entries));

        return new AnalysisResult(stats);
    }

    private Stat generateStat(ConfigurationSet configSet,
                              String representation,
                              ArrayList<DataSourceEntry[]> entries) {
        if (representation.equals("table")) {
            ArrayList<DataSet> dataSets = new ArrayList<>();
            for (SelectedData selectedData : configSet.getSelectedData()) {
                dataSets.add(generateDataSet(configSet.getDataSources(),
                                             selectedData, entries));
            }
            return new Stat(configSet.getLabel(), representation, dataSets);
        }
        return null;
    }

    private DataSet generateDataSet(ArrayList<DataSourceInfo> dataSources,
                                    SelectedData selectedData,
                                    ArrayList<DataSourceEntry[]> entries) {
        String dataSourceLabel = selectedData.getField().split("[.]")[0];
        int dataSourceNumber = findDataSourceByLabel(dataSourceLabel,
                                                     dataSources);
        if (dataSourceNumber == -1) {
            return null;
        }

        String fieldName = selectedData.getFieldName();
        String dataType = entries.get(0)[dataSourceNumber]
                .getFields().get(fieldName).getType();

        DataSet dataSet = new DataSet(selectedData.getFieldName(), dataType);

        for (DataSourceEntry[] entryGroup : entries) {
            String value = entryGroup[dataSourceNumber]
                    .getFields().get(fieldName).getValue();
            dataSet.addValue(value);
        }

        return dataSet;
    }

    private int
    findDataSourceByLabel(String label, ArrayList<DataSourceInfo> dataSources) {
        for (int i = 0; i < dataSources.size(); i++) {
            if (label.equals(dataSources.get(i).getLabel())) {
                return i;
            }
        }

        return -1;
    }
}
