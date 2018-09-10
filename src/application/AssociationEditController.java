package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Association;

public class AssociationEditController {
	@FXML
	TextField textFieldField1;
	
	@FXML
	TextField textFieldField2;
	
	@FXML
	public void apply() {
		Global.addAssociation(new Association(textFieldField1.getText(), textFieldField2.getText()));;
	}
}
