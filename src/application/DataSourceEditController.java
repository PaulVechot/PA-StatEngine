package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import models.DataSourceInfo;

public class DataSourceEditController {
	@FXML
	TextField textFieldLabel;
	
	@FXML
	TextField textFieldType;
	
	@FXML
	public void apply() {
		if(!textFieldLabel.getText().isEmpty()
				&& !textFieldType.getText().isEmpty()) {
			Global.addDataSource(new DataSourceInfo(textFieldLabel.getText(), textFieldType.getText()));
			
			//Close stage
			Stage stage = (Stage) textFieldLabel.getScene().getWindow();
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
}
