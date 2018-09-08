package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import business.ConfigFileReader;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceInfo;
import models.SelectedData;

public class ConfigurationSetsManagerController implements Initializable{

	@FXML
	ListView configurationSetsListView;

    //Load StatsManager FXML
    @FXML
    private void loadStatsManager(ActionEvent event) throws Exception {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass()
        		.getResource("../views/StatsManagerView.fxml"));/* Exception */

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Load the PluginManager FXML
	@FXML
	private void loadPluginManager(ActionEvent event) throws Exception {
	    Node node=(Node) event.getSource();
	    Stage stage=(Stage) node.getScene().getWindow();
	    Parent root = FXMLLoader.load(getClass()
	    		.getResource("../views/PluginManagerView.fxml"));

	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	private void openConfigurationSetsEditWindow() throws Exception {
		//TODO : pass the configuration set by parameters

		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass()
				.getResource("../views/ConfigurationSetsEditView.fxml"));

        stage.setTitle("Edit configuration sets");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("ressources/logo.png"));
        stage.show();
	}
	
	@FXML
	private void sendSelectedConfigSet() throws Exception {
		ConfigurationSet cf = (ConfigurationSet) configurationSetsListView.getSelectionModel().getSelectedItem();
		if (cf != null) {
			ConfigFileReader confFile = new ConfigFileReader("ressources/config.properties");
			String wsAddress = confFile.getProperty("ws.address");
			
			Client client = new Client(wsAddress);
			client.insertNewConfigSet(cf);
		}
		else {
    		Alert alert = new Alert(AlertType.WARNING
    				, "Please select a configuration set to send first");

    		alert.setTitle("Error sending configuration set");
    		alert.setHeaderText("Select a configuration set");
    		alert.show();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/***Get ConfigSet from webservice**/
		/*ConfigFileReader confFile = new ConfigFileReader("ressources/config.properties");
		String wsAddress = confFile.getProperty("ws.address");
		
		Client client = new Client(wsAddress);
		ConfigurationSet[] configSetList  = client.fetchConfigurationSets();
		
		if (configSetList != null) {
			configurationSetsListView.getItems().addAll(configSetList);
		}*/
		
		/***Adding test ConfigSet***/
        ConfigurationSet cf = new ConfigurationSet("configSetTest");
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("opLeft1", "opRight1", "Comparison"));
        
        ArrayList<Association> associations = new ArrayList<>();
        associations.add(new Association("field1", "fiels2"));
        
        ArrayList<SelectedData> selectedData = new ArrayList<>();
        selectedData.add(new SelectedData("fieldSelectedData", "operation"));
        
        ArrayList<DataSourceInfo> dataSources = new ArrayList<>();
        dataSources.add(new DataSourceInfo("DataSource1", "int"));
        
        cf.setId(0);
        cf.setAssociations(associations);
        cf.setConditions(conditions);
        cf.setDataSources(dataSources);
        cf.setSelectedData(selectedData);
        
        configurationSetsListView.getItems().add(cf);
		
		 
		
	}


}
