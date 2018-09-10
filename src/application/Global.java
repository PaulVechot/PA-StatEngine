package application;

import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceInfo;
import models.SelectedData;

public class Global {
	private static ConfigurationSet cf;
	
	public static void newConfigurationSet(String name) {
		cf = new ConfigurationSet(name);
	}
	
	public static ConfigurationSet getConfigurationSet() {
		return cf;
	}
	
	public static void setConfifurationSet(ConfigurationSet confSet) {
		cf = confSet;
	}
	
	public static void addCondition(Condition c) {
		cf.getConditions().add(c);
	}
	
	public static void addAssociation(Association a) {
		cf.getAssociations().add(a);
	}
	
	public static void addSelectedData(SelectedData sd) {
		cf.getSelectedData().add(sd);
	}
	
	public static void addDataSource(DataSourceInfo ds) {
		cf.getDataSources().add(ds);
	}
}
