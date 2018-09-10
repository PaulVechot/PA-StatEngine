package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Condition;

public class ConditionEditController {
	@FXML
	TextField textFieldOpLeft;
	
	@FXML
	TextField textFieldOpRight;
	
	@FXML
	TextField textFieldComparison;
	
	@FXML
	public void apply() {
		if (!textFieldOpLeft.getText().isEmpty()
				&& !textFieldOpRight.getText().isEmpty()
				&& !textFieldComparison.getText().isEmpty()) {
			Global.addCondition(new Condition(textFieldOpLeft.getText(), textFieldOpRight.getText(), textFieldComparison.getText()));
			
			//Close stage
			Stage stage = (Stage) textFieldOpLeft.getScene().getWindow();
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
