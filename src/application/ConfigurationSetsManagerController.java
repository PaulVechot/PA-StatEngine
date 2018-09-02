package application;

import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.ConfigurationSet;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConfigFileReader confFile = new ConfigFileReader("ressources/config.properties");
		String wsAddress = confFile.getProperty("ws.address");
		
		Client client = new Client(wsAddress);
		ConfigurationSet[] configSetList  = client.fetchConfigurationSets();

		configurationSetsListView.getItems().addAll(configSetList);
	}


}
