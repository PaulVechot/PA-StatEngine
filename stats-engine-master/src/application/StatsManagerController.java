package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StatsManagerController implements Initializable{
	
	@FXML
	private TextField pathTextField;
	
	@FXML
	private ListView<Object> statsList;
	
	 @FXML
	 private void openDataSetBrowser(ActionEvent event) throws Exception {
	    	Node node = (Node) event.getSource();
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Choose the file");
	    	
	    	FileChooser.ExtensionFilter jarFilter = new FileChooser
	    			.ExtensionFilter("JAR files (*.jar)", "*.jar");
	    	
	    	fileChooser.getExtensionFilters().add(jarFilter);
	    	
	    	File f = fileChooser.showOpenDialog(node.getScene().getWindow());
	    	if (f!=null) {
	    		pathTextField.setText(f.getAbsolutePath());
	    	}
	    	
	}
	
	//Add a dataSet to the application
    @FXML
    private void addDataSet() throws Exception {
    	if (pathTextField.getText() != null && !pathTextField.getText().trim()
    			.isEmpty()) {
    		
    		statsList.getItems().add(new Label(pathTextField.getText()));
    		
    		//TODO : Create dataset
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING
    				, "Please select a file to add first");
    		
    		alert.setTitle("Error selecting file");
    		alert.setHeaderText("Select a file");
    		alert.show();
    	}
    }
    
    //Remove a dataSet from the application
    @FXML
    private void removeDataSet() {
    	if (statsList.getSelectionModel().getSelectedItem() != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION
    				, "Are you sure you want to remove this plugin ?"
    				, ButtonType.YES, ButtonType.NO);
    		
    		alert.setTitle("Removing plugin");
    		alert.setHeaderText("Remove plugin ?");
    		
    		alert.showAndWait();
    		if (alert.getResult() == ButtonType.YES) {
    			statsList.getItems().remove(statsList.getSelectionModel()
    					.getSelectedItem());
        		
        		//TODO : Remove plugin
    		}

    		
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING
    				, "Please select an item to remove first");
    		
    		alert.setTitle("Error removing item");
    		alert.setHeaderText("Select an item");
    		alert.show();
    	}
    }
	
    //Load the PluginManager FXML
	@FXML
	private void loadPluginManager(ActionEvent event) throws Exception {
	    Node node=(Node) event.getSource();
	    Stage stage=(Stage) node.getScene().getWindow();
	    Parent root = FXMLLoader.load(getClass()
	    		.getResource("../views/PluginManagerView.fxml"));/* Exception */
	    
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	
	
    //Load the ConfigurationSetsManager FXML
	@FXML
	private void loadConfigurationSetsManager(ActionEvent event) throws Exception {
	    Node node=(Node) event.getSource();
	    Stage stage=(Stage) node.getScene().getWindow();
	    Parent root = FXMLLoader.load(getClass()
	    		.getResource("../views/ConfigurationSetsManagerView.fxml"));/* Exception */
	    
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}
