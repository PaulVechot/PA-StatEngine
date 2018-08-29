package application;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PluginManagerController implements Initializable{
	@FXML
	private TextField pathTextField;

	@FXML
	private ListView<Object> pluginList;


    @FXML
    private void openPluginBrowser(ActionEvent event) throws Exception {
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

    //Add a plugin to the application
    @FXML
    private void addPlugin() throws Exception {
    	if (pathTextField.getText() != null && !pathTextField.getText().trim()
    			.isEmpty()) {

    		HBox pluginHB = new HBox();
    		pluginHB.setSpacing(30);
    		Label pluginName = new Label(pathTextField.getText());//Replace with plugin name
    		pluginHB.getChildren().add(new CheckBox());
    		pluginHB.getChildren().add(pluginName);

    		pluginList.getItems().add(pluginHB);

    		loadPlugIn(pathTextField.getText());
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING
    				, "Please select a file to add first");

    		alert.setTitle("Error selecting file");
    		alert.setHeaderText("Select a file");
    		alert.show();
    	}
    }

    private void loadPlugIn(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        JarInputStream jis = new JarInputStream(fis);
        JarEntry entry;

        while ((entry = jis.getNextJarEntry()) != null) {
            if (entry.getName().endsWith(".class")) {
                String fullClassName =
                        entry.getName()
                        .replaceAll("/", "\\.")
                        .replaceAll("\\.class", "");
                String packageName = fullClassName
                        .substring(0, fullClassName.lastIndexOf('.'));
                String className =
                        fullClassName.substring(fullClassName
                                .lastIndexOf('.') + 1);

                PlugInManager.instance.addURL("file://"
                                              + pathTextField.getText());
                PlugInManager.instance.load(packageName, className);
            }
        }
        jis.close();
    }

    //Remove a plugin from the application
    @FXML
    private void removePlugin() {
    	if (pluginList.getSelectionModel().getSelectedItem() != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION
    				, "Are you sure you want to remove this plugin ?"
    				, ButtonType.YES, ButtonType.NO);

    		alert.setTitle("Removing plugin");
    		alert.setHeaderText("Remove plugin ?");

    		alert.showAndWait();
    		if (alert.getResult() == ButtonType.YES) {
    			pluginList.getItems().remove(pluginList.getSelectionModel()
    					.getSelectedItem());

        		//TODO : Remove plugin from application
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
