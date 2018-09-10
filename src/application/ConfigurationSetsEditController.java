package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceInfo;
import models.SelectedData;

public class ConfigurationSetsEditController implements Initializable {
	
	@FXML
	ListView<DataSourceInfo> dataSourceTextView;
	
	@FXML
	ListView<Association> associationTextView;
	
	@FXML
	ListView<Condition> contitionTextView;
	
	@FXML
	ListView<SelectedData> selectedDataTextView;
	
	@FXML
	TextField textFieldName;
	
	@FXML
	public void applyChanges() {
		System.out.println("salut");
		System.out.println(textFieldName.getText());
		if (!textFieldName.getText().isEmpty()
				&& !contitionTextView.getItems().isEmpty()
				&& !associationTextView.getItems().isEmpty()
				&& !selectedDataTextView.getItems().isEmpty()
				&& !dataSourceTextView.getItems().isEmpty()) {
			
			Global.newConfigurationSet(textFieldName.getText());
	        
	        Global.getConfigurationSet().setConditions(new ArrayList<Condition>(contitionTextView.getItems()));
	        
	        Global.getConfigurationSet().setAssociations(new ArrayList<Association>(associationTextView.getItems()));
	        
	        Global.getConfigurationSet().setSelectedData(new ArrayList<SelectedData>(selectedDataTextView.getItems()));
	        
	        Global.getConfigurationSet().setDataSources(new ArrayList<DataSourceInfo>(dataSourceTextView.getItems()));
	        
			//Close stage
			Stage stage = (Stage) textFieldName.getScene().getWindow();
			stage.close();
		}
		else {
    		Alert alert = new Alert(AlertType.ERROR
    				, "Please fill all field");

    		alert.setTitle("Error some fields were not filled correctly");
    		alert.setHeaderText("Fill all the fields");
    		alert.show();
		}
       
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
	
	@FXML
	public void createDataSource() throws Exception {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass()
				.getResource("../views/DataSourceEditView.fxml"));/* Exception */
		
        stage.setTitle("New data source");
        
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("ressources/logo.png"));
        stage.show();
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
	
	@FXML
	public void createAssociation() throws Exception {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass()
				.getResource("../views/AssociationEditView.fxml"));/* Exception */
		
        stage.setTitle("New association");
        
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("ressources/logo.png"));
        stage.show();
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
	
	@FXML
	public void createCondition() throws Exception {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass()
				.getResource("../views/ConditionEditView.fxml"));/* Exception */
		
        stage.setTitle("New condition");
        
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("ressources/logo.png"));
        stage.show();
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
	
	@FXML
	public void createSelectedData() throws Exception{
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass()
				.getResource("../views/SelectedDataEditView.fxml"));/* Exception */
		
        stage.setTitle("New selected data");
        
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("ressources/logo.png"));
        stage.show();
	}
	
	@FXML
	public void update() {
		textFieldName.setText(Global.getConfigurationSet().getLabel());
		
		
		dataSourceTextView.getItems().clear();
		dataSourceTextView.getItems().addAll(Global.getConfigurationSet().getDataSources());
		
		associationTextView.getItems().clear();
		associationTextView.getItems().addAll(Global.getConfigurationSet().getAssociations());
		
		contitionTextView.getItems().clear();
		contitionTextView.getItems().addAll(Global.getConfigurationSet().getConditions());
		
		selectedDataTextView.getItems().clear();
		selectedDataTextView.getItems().addAll(Global.getConfigurationSet().getSelectedData());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/***Adding test ConfigSet***/
        /*ConfigurationSet cf = new ConfigurationSet("configSetTest");
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
        	
        textFieldName.setText("TestConfigSet");
		dataSourceTextView.getItems().addAll(dataSources);
		associationTextView.getItems().addAll(associations);
		contitionTextView.getItems().addAll(conditions);
		selectedDataTextView.getItems().addAll(selectedData);*/
        
		Global.newConfigurationSet("test");

	}
}
