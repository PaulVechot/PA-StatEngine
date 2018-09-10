package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Association;

public class AssociationEditController {
	@FXML
	TextField textFieldField1;
	
	@FXML
	TextField textFieldField2;
	
	@FXML
	public void apply() {
		if(!textFieldField1.getText().isEmpty()
				&& !textFieldField2.getText().isEmpty()) {
			Global.addAssociation(new Association(textFieldField1.getText(), textFieldField2.getText()));
			
			//Close stage
			Stage stage = (Stage) textFieldField1.getScene().getWindow();
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
