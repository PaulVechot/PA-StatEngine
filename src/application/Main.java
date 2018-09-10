package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;

import com.google.gson.Gson;

import business.ConfigFileReader;
import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.AnalysisResult;
import models.Association;
import models.Condition;
import models.ConfigurationSet;
import models.DataSourceInfo;
import models.SelectedData;




public class Main extends Application {

    private Stage stage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        
		try {
			//redirectError();

			
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
    
    //Redirect the err stream to a file
    public void redirectError() throws FileNotFoundException{
    	
    	FileOutputStream ferr;
		ferr = new FileOutputStream("err.log");
		PrintStream ps = new PrintStream(ferr);
		System.setErr(ps);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
