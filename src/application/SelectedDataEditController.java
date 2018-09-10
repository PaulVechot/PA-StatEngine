package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.SelectedData;

public class SelectedDataEditController {
	@FXML
	TextField textFieldVariable;
	
	@FXML
	TextField textFieldOperation;
	
	@FXML
	public void apply() {
		if(!textFieldVariable.getText().isEmpty()
				&& !textFieldOperation.getText().isEmpty()) {
			Global.addSelectedData(new SelectedData(textFieldVariable.getText(), textFieldOperation.getText()));
			
			//Close stage
			Stage stage = (Stage) textFieldOperation.getScene().getWindow();
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
