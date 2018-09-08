package application;


import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.ConfigurationSet;

public class ConfigurationSetsEditController implements Initializable {
	
	@FXML
	ListView dataSourceTextView;
	
	@FXML
	ListView associationTextView;
	
	@FXML
	ListView contitionTextView;
	
	@FXML
	ListView selectedDataTextView;
	
	ConfigurationSet newConfigSet;
	
	@FXML
	public void applyChanges() {
		
	}
	
	//Open the edit view for data sources
	@FXML
	public void dataSourceClicked() throws Exception {
		if (dataSourceTextView.getSelectionModel().getSelectedItem() != null) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource("../views/DataSourceEditView.fxml"));/* Exception */
			
	        stage.setTitle("Data source edit : "+dataSourceTextView.getSelectionModel()
	        			.getSelectedItem().toString());
	        
	        stage.setScene(new Scene(root));
	        stage.getIcons().add(new Image("ressources/logo.png"));
	        stage.show();
		}
	}
	
	//Open the edit view for associations
	@FXML
	public void associationClicked() throws Exception {
		if (associationTextView.getSelectionModel().getSelectedItem() != null) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource("../views/AssociationEditView.fxml"));/* Exception */
			
	        stage.setTitle("Association edit : "+associationTextView.getSelectionModel()
	        			.getSelectedItem().toString());
	        
	        stage.setScene(new Scene(root));
	        stage.getIcons().add(new Image("ressources/logo.png"));
	        stage.show();
		}
	}
	
	//Open the edit view for conditions
	@FXML
	public void conditionClicked() throws Exception {
		if (contitionTextView.getSelectionModel().getSelectedItem() != null) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource("../views/ConditionEditView.fxml"));/* Exception */
			
	        stage.setTitle("Condition edit : "+contitionTextView.getSelectionModel()
	        			.getSelectedItem().toString());
	        
	        stage.setScene(new Scene(root));
	        stage.getIcons().add(new Image("ressources/logo.png"));
	        stage.show();
		}
	}
	
	//Open the edit view for selected datas
	@FXML
	public void selectedDataClicked() throws Exception {
		if (selectedDataTextView.getSelectionModel().getSelectedItem() != null) {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource("../views/SelectedDataEditView.fxml"));/* Exception */
			
	        stage.setTitle("Selected data edit : "+selectedDataTextView.getSelectionModel()
	        			.getSelectedItem().toString());
	        
	        stage.setScene(new Scene(root));
	        stage.getIcons().add(new Image("ressources/logo.png"));
	        stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//test items
		dataSourceTextView.getItems().add("testData1");
		dataSourceTextView.getItems().add("testData2");
		
		associationTextView.getItems().add("testAssociation1");
		
		contitionTextView.getItems().add("testCondition1");
		
		selectedDataTextView.getItems().add("testSelectedData1");
		
		
		
	}
}
