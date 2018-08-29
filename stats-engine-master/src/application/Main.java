package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;




public class Main extends Application {

    private Stage stage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        
		try {
			stage = primaryStage;
	        root = FXMLLoader.load(getClass()
	        		.getResource("../views/StatsManagerView.fxml"));
	        
	        stage.setTitle("Stats Engine");
	        stage.setScene(new Scene(root));
	        stage.getIcons().add(new Image("ressources/logo.png"));
	        stage.show();

			/*PlugInManager.instance.addURL("file:///tmp/TestPlugIn.jar");
			PlugInManager.instance.load("plugIn", "TestPlugIn");
			Hashtable<String, PlugIn> plugIns =
			        PlugInManager.instance.getPlugIns();
			for (int i = 0; i < plugIns.size(); i++) {
			    String name = (String) plugIns.keySet().toArray()[i];
			    System.out.println("plugIn " + i + ": " + name);
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}
    }


    public static void main(String[] args) {
        launch(args);
    }
}
