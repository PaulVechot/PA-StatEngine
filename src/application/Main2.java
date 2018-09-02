package application;

import java.util.ArrayList;
import java.util.Hashtable;

import com.google.gson.Gson;

import business.ConditionsEngine;
import business.DataSourceInterface;
import business.StatsGenerator;
import client.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import models.AnalysisResult;
import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceEntry;
import models.DataSourceInfo;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/*BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainView.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			PlugInManager.instance.addURL("file:///tmp/TestPlugIn.jar");
			PlugInManager.instance.load("plugIn", "TestPlugIn");
			Hashtable<String, PlugIn> plugIns =
			        PlugInManager.instance.getPlugIns();
			for (int i = 0; i < plugIns.size(); i++) {
			    String name = (String) plugIns.keySet().toArray()[i];
			    System.out.println("plugIn " + i + ": " + name);
			}*/

			/* **** TESTING Client CLASS *** */
			Client client = new Client("http://localhost:8080/web-service");

			DataSourceInfo[] dataSources = client.fetchDataSourceInfos();
			for (DataSourceInfo dataSource : dataSources) {
			    System.out.println(dataSource.toString());
			}

			ConfigurationSet[] configSets = client.fetchConfigurationSets();
			for (ConfigurationSet configSet : configSets) {
			    System.out.println(configSet.toString());
			}

			DataSourceInfo newDataSource = new DataSourceInfo("new_source",
			                                                  "xml_file");
			newDataSource.addAccessInfo("path", "/tmp/source.xml");
			client.insertNewDataSource(newDataSource);

			ConfigurationSet configSet = new ConfigurationSet("new_configset");
			configSet.getConditions().add(new Condition("source.var",
			                                            "source.var2", "="));
			configSet.getAssociations().add(new Association("source.var",
			                                                "source2.var"));
			client.insertNewConfigSet(configSet);

			client.deleteConfigSet(configSet);
			client.deleteDataSource(newDataSource);
			/* ***************************** */

			/* **** TESTING DataSourceInterface CLASS **** */
			PlugInManager.instance.addURL("file:///tmp/TestPlugIn.jar");
			PlugInManager.instance.load("plugIn", "TestPlugIn");
			Hashtable<String, PlugIn> plugIns =
			        PlugInManager.instance.getPlugIns();
			for (int i = 0; i < plugIns.size(); i++) {
			    String name = (String) plugIns.keySet().toArray()[i];
			    System.out.println("plugIn " + i + ": " + name);
			}

			DataSourceInterface jsonFileInterface =
			        DataSourceInterface.getForDataSourceType("json_file");

			DataSourceEntry[] authors =
			        jsonFileInterface.fetchEntries(dataSources[3]); // JSON
			DataSourceEntry[] books =
			        jsonFileInterface.fetchEntries(dataSources[0]); // JSON

			for (int i = 0; i < authors.length; i++) {
			    System.out.println("author " + i + ": " + authors[i].toString());
            }
			for (int i = 0; i < books.length; i++) {
			    System.out.println("book " + i + ": " + books[i].toString());
            }
			/* ******************************************* */

			/* **** TESTING ConditionsEngine CLASS **** */
			DataSourceEntry[][] entriesToCheck = { books, authors };
			ConditionsEngine conditionsEngine = new ConditionsEngine();

			ArrayList<DataSourceEntry[]> matchingEntries = conditionsEngine
			        .applyConditionsAndAssociations(configSets[0],
                                                    entriesToCheck);

			for (int i = 0; i < matchingEntries.size(); i++) {
			    System.out.println("matching entry " + i + ": ");
			    DataSourceEntry[] matchEntries = matchingEntries.get(i);
			    for (int j = 0; j < matchEntries.length; j++) {
			        System.out.println(matchEntries[j].toString());
                }
            }
			/* **************************************** */

			/* **** TESTING StatsGenerator CLASS **** */
			StatsGenerator statsGenerator = new StatsGenerator();
			AnalysisResult analysisResult =
			        statsGenerator.generateAnalysisResult(configSets[0],
			                                              matchingEntries);
			System.out.println("Analysis result: "
			                   + new Gson().toJson(analysisResult));
			/* ************************************** */
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
